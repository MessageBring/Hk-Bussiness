package cn.hk.service.impl;

import cn.hk.common.BusinessException;
import cn.hk.common.constants.RedisKeyConstants;
import cn.hk.common.enums.RespEnums;
import cn.hk.common.service.IRedisService;
import cn.hk.common.utils.*;
import cn.hk.dao.service.IUsersMapperService;
import cn.hk.model.po.Users;
import cn.hk.model.vo.UserVO;
import cn.hk.service.IToBeAnUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service("IToBeAnUserService")
@RequiredArgsConstructor
@Slf4j
public class ToBeAnUserService implements IToBeAnUserService {

    private final IUsersMapperService usersMapperService;

    private final IRedisService redisService;

    @Value("${user.encrypt.rsa-key}")
    private String rsaKey;
    @Override
    public void registerUser(String nickName, String contact, int gender,int age,String password) {
        boolean isPhone = StringUtil.isPhone(contact);
        boolean isEmail = StringUtil.isEmail(contact);
        Users users = usersMapperService.getOne(new QueryWrapper<Users>().lambda()
                .eq(isPhone, Users::getPhone, contact)
                .eq(isEmail, Users::getEmail, contact)
        );
        if (ObjectUtils.isEmpty(users)){
            Users newUser = new Users();
            Long newUserId = UniqueIdUtil.getSnowFlakeId();
            newUser.setUserId(newUserId);
            newUser.setNickName(nickName);
            if (isEmail){
                newUser.setEmail(contact);
            }else {
                newUser.setPhone(contact);
            }
            String pwd = EncryptUtil.getEncrypt("RSA",rsaKey).decrypt(password);
            newUser.setAge(age);
            newUser.setGender(gender);
            newUser.setPassword(MD5Util.md5Hash(pwd));
            usersMapperService.addNewUserRecord(newUser);
        }
    }

    @Override
    public UserVO login(String account, String password) {
        boolean isPhone = StringUtil.isPhone(account);
        boolean isEmail = StringUtil.isEmail(account);
        Users users;
        users = usersMapperService.getOne(new QueryWrapper<Users>()
                .lambda()
                .eq(isPhone, Users::getPhone, account)
                .eq(isEmail, Users::getEmail, account)
        );
        if (users==null) {
            throw new BusinessException(RespEnums.NO_DATA.getCode(), "User does not exist");
        }
        if (users.getLoginErrCnt()>=5){
            throw new BusinessException(RespEnums.OVER_LOG_ERR_COUNT);
        }

        String pwd = EncryptUtil.getEncrypt("RSA",rsaKey).decrypt(password);
        String mdPwd = MD5Util.md5Hash(pwd);

        if (mdPwd.equals(users.getPassword())) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(users,userVO);
            userVO.setToken(JWTUtil.getToken(userVO.getUserId(),userVO.toString()));
            redisService.setVal(String.format(RedisKeyConstants.TOKEN_PREFIX,userVO.getUserId()),userVO.getToken(),RedisKeyConstants.TOKEN_TTL);
            return userVO;
        }
        Users updateUser = new Users();
        updateUser.setUserId(users.getUserId());
        updateUser.setLoginErrCnt(users.getLoginErrCnt()+1);
        usersMapperService.updateById(updateUser);
        throw new BusinessException(RespEnums.INCORRECT_PASS);
    }
}

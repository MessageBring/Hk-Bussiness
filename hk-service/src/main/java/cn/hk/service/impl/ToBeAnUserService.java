package cn.hk.service.impl;

import cn.hk.common.utils.StringUtil;
import cn.hk.common.utils.UniqueIdUtil;
import cn.hk.dao.service.IUsersMapperService;
import cn.hk.model.po.Users;
import cn.hk.service.IToBeAnUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service("IToBeAnUserService")
@RequiredArgsConstructor
@Slf4j
public class ToBeAnUserService implements IToBeAnUserService {

    private final IUsersMapperService usersMapperService;
    @Override
    public void registerUser(String nickName, String contact, int gender,int age) {
        boolean isPhone = StringUtil.checkPhone(contact);
        boolean isEmail = StringUtil.checkEmail(contact);
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
            newUser.setAge(age);
            newUser.setGender(gender);
            usersMapperService.addNewUserRecord(newUser);
        }
    }
}

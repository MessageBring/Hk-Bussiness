package cn.hk.dao.service.impl;

import cn.hk.dao.mapper.UsersMapper;
import cn.hk.dao.service.IUsersMapperService;
import cn.hk.model.po.Users;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UsersMapperService extends ServiceImpl<UsersMapper, Users> implements IUsersMapperService {

    private UsersMapper usersMapper = this.getBaseMapper();

    @Override
    public Boolean checkPhoneExist(String phone) {
        long count = this.count(new QueryWrapper<Users>().lambda()
                .eq(Users::getPhone,phone)
                .eq(Users::getIsDeleted,0)
        );
        if (count>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkEmailExist(String email) {
        long count = this.count(new QueryWrapper<Users>().lambda()
                .eq(Users::getEmail,email)
                .eq(Users::getIsDeleted,0)
        );
        if (count>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean addNewUserRecord(Users user) {
        return this.save(user);
    }
}

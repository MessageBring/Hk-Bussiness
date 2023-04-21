package cn.hk.dao.service.impl;

import cn.hk.dao.mapper.UsersMapper;
import cn.hk.dao.service.IUsersMapperService;
import cn.hk.model.po.Users;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UsersMapperService extends ServiceImpl<UsersMapper, Users> implements IUsersMapperService {

    @Override
    public boolean addNewUserRecord(Users user) {
        return this.save(user);
    }
}

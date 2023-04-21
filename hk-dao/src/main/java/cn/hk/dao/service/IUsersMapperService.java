package cn.hk.dao.service;

import cn.hk.model.po.Users;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUsersMapperService extends IService<Users> {
    boolean addNewUserRecord(Users user);
}

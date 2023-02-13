package cn.hk.dao.service;

import cn.hk.model.po.Users;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUsersMapperService extends IService<Users> {
    Boolean checkPhoneExist(String phone);

    Boolean checkEmailExist(String email);

    boolean addNewUserRecord(Users user);
}

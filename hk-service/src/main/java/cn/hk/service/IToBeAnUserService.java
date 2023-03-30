package cn.hk.service;

import cn.hk.model.vo.UserVO;

public interface IToBeAnUserService {
    void registerUser(String nickName,String contact,int gender,int age,String password);

    UserVO login(String account,String password);
}

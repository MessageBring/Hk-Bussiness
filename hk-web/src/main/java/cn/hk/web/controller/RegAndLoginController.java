package cn.hk.web.controller;

import cn.hk.common.resp.GlobalResponse;
import cn.hk.model.vo.UserVO;
import cn.hk.service.IToBeAnUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toBeAnUser")
@RequiredArgsConstructor
public class RegAndLoginController {

    private final IToBeAnUserService toBeAnUserService;

    @PostMapping("/register")
    public GlobalResponse register(@RequestParam("nickName")String nickName,
                                   @RequestParam("contact")String contact,
                                   @RequestParam("age")int age,
                                   @RequestParam("gender")int gender,
                                   @RequestParam("password")String password) {
        toBeAnUserService.registerUser(nickName, contact, gender, age,password);
        return GlobalResponse.success();
    }

    @PostMapping("login")
    public GlobalResponse<UserVO> login(@RequestParam("account")String account,
                                        @RequestParam("password")String password){
        return null;
    }
}

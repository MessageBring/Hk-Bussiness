package cn.hk.web.controller;

import cn.hk.common.resp.GlobalResponse;
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
                                   @RequestParam("int")int gender) {
        toBeAnUserService.registerUser(nickName, contact, gender, age);
        return GlobalResponse.success();
    }
}

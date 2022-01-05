package cn.hk.web.controller;

import cn.hk.common.resp.GlobalResponse;
import cn.hk.model.dto.ProjectTestDTO;
import cn.hk.service.IHelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloController {
    private final IHelloService helloService;

    @GetMapping("/getProjectTests")
    public GlobalResponse getAllProjectTest(){
        return GlobalResponse.success(helloService.queryAllProjectTest());
    }

    @PostMapping("/addOneProjectTest")
    public GlobalResponse addOneProjectTest(@RequestBody ProjectTestDTO projectTestDTO){
        helloService.addOneProjectTestRecord(projectTestDTO);
        return GlobalResponse.success();
    }
}

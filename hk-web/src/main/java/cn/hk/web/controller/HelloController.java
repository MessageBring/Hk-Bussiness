package cn.hk.web.controller;

import cn.hk.common.resp.GlobalResponse;
import cn.hk.model.dto.ProjectTestDTO;
import cn.hk.model.po.ProjectTest;
import cn.hk.model.vo.ProjectTestVo;
import cn.hk.service.IHelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloController {
    private final IHelloService helloService;

    @GetMapping("/getProjectTests")
    public GlobalResponse<List<ProjectTestVo>> getAllProjectTest(){
        return GlobalResponse.success(helloService.queryAllProjectTest());
    }

    @PostMapping("/addOneProjectTest")
    public GlobalResponse addOneProjectTest(@RequestBody ProjectTestDTO projectTestDTO){
        helloService.addOneProjectTestRecord(projectTestDTO);
        return GlobalResponse.success();
    }

    @GetMapping("/testShorterUrl")
    public void testShorterUrl(HttpServletResponse response){
        response.setStatus(302);
        response.setHeader("location","https://www.baidu.com");
    }

    @GetMapping("/testRestTemplateGET")
    public GlobalResponse<String> testRestTemplate(@RequestParam(name = "url") String url){
        return GlobalResponse.success(helloService.testRestTemplate(url));
    }
}

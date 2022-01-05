package cn.hk;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.hk.dao.mapper")
@Slf4j
public class HkCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(HkCoreApplication.class,args);
        log.info("application is running......");
    }
}

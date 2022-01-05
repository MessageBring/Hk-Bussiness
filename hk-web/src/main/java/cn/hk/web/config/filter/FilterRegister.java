package cn.hk.web.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegister {

    @Bean
    public FilterRegistrationBean registeCorsFilter(){
        FilterRegistrationBean<CorsFilter> corsFilterRegister = new FilterRegistrationBean<>();
        corsFilterRegister.setFilter(new CorsFilter());
        corsFilterRegister.setOrder(0);
        corsFilterRegister.addUrlPatterns("/*");
        return corsFilterRegister;
    }
}

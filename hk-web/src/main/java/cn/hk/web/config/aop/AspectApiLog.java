package cn.hk.web.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class AspectApiLog {

    @Around("execution(public * cn.hk.web.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURL().toString();
        String requestMethod = request.getMethod();
        long start = System.currentTimeMillis();
        log.info("=====api start, request url:{}, method:{}",url,requestMethod);
        log.info("=====api param:{}", Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("=====api end, time to spend: {}ms",end-start);
        return result;
    }
}

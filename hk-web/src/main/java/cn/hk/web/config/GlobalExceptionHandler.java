package cn.hk.web.config;

import cn.hk.common.BusinessException;
import cn.hk.common.enums.RespEnums;
import cn.hk.common.resp.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public GlobalResponse exceptionHandler(HttpServletRequest request, Exception e) {
        log.info("Server Exception:",e);
        return GlobalResponse.fail(RespEnums.SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public GlobalResponse bussinessExceptionHandler(HttpServletRequest request, BusinessException e) {
        log.info("Bussiness Exception:{},{}",e.getCode(),e.getErrMsg());
        return GlobalResponse.fail(e.getCode(),e.getErrMsg());
    }
}

package cn.hk.web.config.interceptor;

import cn.hk.common.BusinessException;
import cn.hk.common.constants.RedisKeyConstants;
import cn.hk.common.context.BasicContext;
import cn.hk.common.enums.RespEnums;
import cn.hk.common.service.IRedisService;
import cn.hk.common.utils.JWTUtil;
import cn.hk.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthVerifyInterceptor implements HandlerInterceptor {

    @Autowired
    IRedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=====start to auth verify");
        //获取header的auth token,为空抛出token invalid异常
        String token = request.getHeader("Authorization");
        if (StringUtil.isEmpty(token)){
            throw new BusinessException(RespEnums.TOKEN_INVALID);
        }
        //校验token，校验失败抛出异常
        String userId = JWTUtil.verifyToken(token);
        //根绝解析的userId获取redis缓存token，缓存为空，说明为异常token，抛出异常
        String cacheToken = redisService.getVal(String.format(RedisKeyConstants.TOKEN_PREFIX,userId));
        if (StringUtil.isEmpty(cacheToken)){
            throw new BusinessException(RespEnums.TOKEN_INVALID);
        }
        //判断header和redis的token是否相等，相等将解析的userId存入线程本地变量，方便后续读取
        if (cacheToken.equals(token)){
            BasicContext.threadLocal.set(userId);
            log.info("request user:{}",userId);
            return true;
        }
        throw new BusinessException(RespEnums.TOKEN_INVALID);
    }
}

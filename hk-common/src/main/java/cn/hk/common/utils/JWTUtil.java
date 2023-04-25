package cn.hk.common.utils;

import cn.hk.common.BusinessException;
import cn.hk.common.enums.RespEnums;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class JWTUtil {

    private static String ENCRYPT_KEY="兰亭序";

    public static String getToken(long userId,String param){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7);
        JWTCreator.Builder builder = JWT.create();
        builder.withAudience(String.valueOf(userId)).withIssuedAt(new Date()).withClaim("user",param);

        Algorithm algorithm = Algorithm.HMAC256(ENCRYPT_KEY);
        return builder.withExpiresAt(calendar.getTime()).sign(algorithm);
    }

    public static String verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(ENCRYPT_KEY)).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getAudience().get(0);
        }catch (Exception e) {
            throw new BusinessException(RespEnums.TOKEN_INVALID);
        }
    }
}

package com.cchao.pinbox.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cchao.pinbox.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author : cchao
 * @version 2019-01-31
 */
public class JWTUtil {
    // 过期时间
    private static final long EXPIRE_TIME = 24 * 3600 * 1000;

    private static final String HEADER_NAME = "Authorization";
    private static final String BEARER = "Bearer";

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(boolean isThrow, String token, String username, long userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(Constant.USER_NAME, username)
                    .withClaim(Constant.USER_ID, userId)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            if (isThrow) {
                throw exception;
            } else {
                return false;
            }
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Constant.USER_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(HttpServletRequest httpServletRequest) {
//        String token = StringUtils.removeFirst(httpServletRequest.getHeader(HEADER_NAME), BEARER);
        String token = httpServletRequest.getHeader(HEADER_NAME);
        return getUsername(token);
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(Constant.USER_ID).asLong();
        } catch (JWTDecodeException e) {
            return 0;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, long id, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim(Constant.USER_NAME, username)
                .withClaim(Constant.USER_ID, id)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}

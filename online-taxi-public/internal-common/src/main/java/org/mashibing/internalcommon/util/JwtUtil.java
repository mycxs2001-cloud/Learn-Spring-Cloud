package org.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.mashibing.internalcommon.dto.TokenResult;

import java.util.Calendar;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 14:13
 */
public class JwtUtil {

    //盐
    private static final String SIGN = "CPFmsb!@#$$";

    private static final String JWT_KEY_PHONE = "phone";

    //乘客 1 司机 2
    private static final String JWT_KEY_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "tokenType";

    //生成token
    public static String generatorToken(String passengerPhone, String identity, String tokenType) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        //token过期时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE,1);
//        Date date= calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        //整合map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        //整合过期时间
        //builder.withExpiresAt(date);

        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }


    //解析token
    public static TokenResult parseToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SIGN); // 确保与签发时的 secret 和算法一致
        JWTVerifier verifier = JWT.require(algorithm)
                // .withIssuer("your-issuer")       // 如签发时有 issuer/audience，这里也要校验
                // .withAudience("your-aud")
                .acceptLeeway(5)                    // 时钟偏差容忍 5 秒（可选）
                .build();

        DecodedJWT jwt = verifier.verify(token);
        //DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = jwt.getClaim(JWT_KEY_PHONE).asString();
        String identity = jwt.getClaim(JWT_KEY_IDENTITY).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    public static void main(String[] args) {

        String s = generatorToken("15573558065", "1","access");
        System.out.println("生成token :" + s);

        System.out.println("解析-----------");

        TokenResult tokenResult = parseToken(s);
        System.out.println("身份: " + tokenResult.getIdentity());
        System.out.println("手机号: " + tokenResult.getPhone());

    }
}

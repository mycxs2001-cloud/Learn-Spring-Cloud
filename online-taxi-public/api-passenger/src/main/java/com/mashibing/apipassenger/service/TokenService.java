package com.mashibing.apipassenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import org.mashibing.internalcommon.constant.CommonStatusEnum;
import org.mashibing.internalcommon.constant.IdentityConstant;
import org.mashibing.internalcommon.constant.TokenConstant;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.dto.TokenResult;
import org.mashibing.internalcommon.response.TokenResponse;
import org.mashibing.internalcommon.util.JwtUtil;
import org.mashibing.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 22:29
 */
@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc) {
        //解析refresh
        TokenResult tokenResult = JwtUtil.checkToken(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //从refresh 中解析出 phone 和identity
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        //获得refreshTokenKey
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);
        //去redis中读取 refresh
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        //判断是否一致
        if (StringUtils.isBlank(refreshTokenRedis) || (!refreshTokenRedis.trim().equals(refreshTokenSrc.trim()))) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        //生成双Token
        String accessToken = JwtUtil.generatorToken(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtil.generatorToken(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);
        //获得accessTokenKey
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken,31, TimeUnit.DAYS);


        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }

}

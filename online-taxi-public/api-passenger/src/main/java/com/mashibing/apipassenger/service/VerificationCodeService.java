package com.mashibing.apipassenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import net.sf.json.JSONObject;
import org.mashibing.internalcommon.constant.CommonStatusEnum;
import org.mashibing.internalcommon.constant.IdentityConstant;
import org.mashibing.internalcommon.constant.TokenConstant;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.request.VerificationCodeDTO;
import org.mashibing.internalcommon.response.NumberCodeResponse;
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
 * @date: 2025-09-05 13:40
 */
@Service
public class VerificationCodeService {


    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    //获取验证码
    public ResponseResult generatorCode(String passengerPhone) {
        //调用短信服务,获取验证码
        System.out.println("调用验证码服务,获取验证码 :"+passengerPhone);
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("remote number code : " + numberCode);

        //存入redis
        System.out.println("存入redis");
        String key = RedisPrefixUtils.passengerKeyByPhone(passengerPhone);

        //存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        //返回值


        return ResponseResult.success("");
    }

    //验证验证码是否有效
    public ResponseResult checkCode(String passengerPhone,String verificationCode){



        System.out.println("根据手机号、去redis获取验证码");

        //生成redis Key
        String key = RedisPrefixUtils.passengerKeyByPhone(passengerPhone);

        String redisKey = stringRedisTemplate.opsForValue().get(key);

        System.out.println("redis 中的 value :"+redisKey);

        System.out.println("校验验证码");
        if (StringUtils.isBlank( redisKey)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_ERROR.getCode(), CommonStatusEnum.VERIFICATION_ERROR.getValue(),redisKey);
        }
        if (!verificationCode.trim().equals(redisKey.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_ERROR.getCode(), CommonStatusEnum.VERIFICATION_ERROR.getValue(),redisKey);
        }
        System.out.println("判断原来是否有用户");
        VerificationCodeDTO verificationCodeDTO=new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);


        //"颁发令牌" identity 应该用常量
        String accessToken=JwtUtil.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.ACCESS_TOKEN_TYPE);
        String refreshToken=JwtUtil.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstant.REFRESH_TOKEN_TYPE);

        //将accessTokenKey传入redis中
        String accessTokenKey=RedisPrefixUtils.generatorTokenKey(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenConstant.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        //将refReshTokenKey传入redis中
        String refReshTokenKey=RedisPrefixUtils.generatorTokenKey(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenConstant.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refReshTokenKey,refReshTokenKey,31,TimeUnit.DAYS);


        //响应token
        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        System.out.println(accessToken);
        return ResponseResult.success(tokenResponse);

    }


}

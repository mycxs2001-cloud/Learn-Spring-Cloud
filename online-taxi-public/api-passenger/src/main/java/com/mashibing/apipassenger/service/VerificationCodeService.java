package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import net.sf.json.JSONObject;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.response.NumberCodeResponse;
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
    private StringRedisTemplate stringRedisTemplate;

    private String verificationCodePrefix = "passenger-verification-code-";

    public ResponseResult generatorCode(String passengerPhone) {
        //调用短信服务,获取验证码
        System.out.println("调用验证码服务,获取验证码 :"+passengerPhone);
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("remote number code : " + numberCode);

        //存入redis
        System.out.println("存入redis");
        String key = verificationCodePrefix + numberCode;

        //存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        //返回值


        return ResponseResult.success("");
    }

    ;

}

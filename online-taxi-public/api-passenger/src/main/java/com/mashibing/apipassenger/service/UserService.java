package com.mashibing.apipassenger.service;

import lombok.extern.slf4j.Slf4j;
import org.mashibing.internalcommon.constant.CommonStatusEnum;
import org.mashibing.internalcommon.dto.PassengerUser;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.dto.TokenResult;
import org.mashibing.internalcommon.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-10 11:33
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult getUserByAccessToken(String accessToken){
          log.info("request accessToken :"+accessToken);
        //解析refresh
        TokenResult tokenResult = JwtUtil.checkToken(accessToken);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //从refresh 中解析出 phone 和identity
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();


        PassengerUser passengerUser=new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("不知道");
        passengerUser.setPassengerGender(1);
        passengerUser.setPassengerPhone(phone);


        return ResponseResult.success(passengerUser);
    }
}

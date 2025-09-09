package com.mashibing.servicepassengeruser.controller;

import com.mashibing.servicepassengeruser.service.UserService;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-08 18:29
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone(); //获取手机号码
        System.out.println("手机号码: "+ passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

}

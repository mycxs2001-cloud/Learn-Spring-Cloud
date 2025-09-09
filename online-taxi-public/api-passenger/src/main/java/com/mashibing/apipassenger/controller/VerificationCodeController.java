package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.VerificationCodeService;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 13:33
 */
@RestController
public class VerificationCodeController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping("verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println( "接收到的手机参数 :" +passengerPhone);
        ResponseResult generatorCode = verificationCodeService.generatorCode(passengerPhone);

        return generatorCode;
    }

    @PostMapping("verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();

        System.out.println("手机号: "+ passengerPhone +" 验证码 :"+ verificationCode);
        ResponseResult checkVerification = verificationCodeService.checkCode(passengerPhone,verificationCode);

        return checkVerification;
    }





}

package com.mashibing.apipassenger.request;

import lombok.Data;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 13:36
 */
@Data
public class VerificationCodeDTO {
    private String passengerPhone; //手机号码

    private String verificationCode; //验证码


}

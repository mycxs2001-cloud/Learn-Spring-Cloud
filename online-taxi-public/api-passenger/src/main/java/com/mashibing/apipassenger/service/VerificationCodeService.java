package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import net.sf.json.JSONObject;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String generatorCode(String passengerPhone) {
    //调用短信服务,获取验证码
    System.out.println("调用验证码服务,获取验证码 ");
    ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
     int numberCode = numberCodeResponse.getData().getNumberCode();
     System.out.println("remote number code : "+numberCode);

        //存入redis
        System.out.println("存入redis");

        //返回值

        JSONObject rv = new JSONObject();
        rv.put("code", "1");
        rv.put("message", "success");
        return rv.toString();
    }

    ;

}

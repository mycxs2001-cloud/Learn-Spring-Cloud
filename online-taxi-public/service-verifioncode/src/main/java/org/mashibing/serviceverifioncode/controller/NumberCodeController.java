package org.mashibing.serviceverifioncode.controller;

import net.sf.json.JSONObject;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 17:11
 */
@RestController
public class NumberCodeController {


    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size ){

        //生成验证码
        double mathRandom=(Math.random()*9+1)*(Math.pow(10,size-1));
        //变成六位数
        int num = (int)mathRandom;

        System.out.println("generator code:"+num);

        NumberCodeResponse numberCodeResponse=new NumberCodeResponse();
        numberCodeResponse.setNumberCode(num);

        return ResponseResult.success(numberCodeResponse);
    }

}

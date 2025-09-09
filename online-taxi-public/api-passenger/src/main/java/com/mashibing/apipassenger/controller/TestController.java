package com.mashibing.apipassenger.controller;

import org.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 11:48
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    /**
     * 需要Token
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult authTest(){
        return ResponseResult.success("authTest");
    }


    /**
     * 没有Token也能正常返回
     * @return
     */
    @GetMapping("/noauthTest")
    public ResponseResult noauthTest(){
        return ResponseResult.success("noauthTest");
    }

}

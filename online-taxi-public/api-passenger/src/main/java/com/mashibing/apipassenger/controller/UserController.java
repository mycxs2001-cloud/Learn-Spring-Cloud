package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-10 11:29
 */
@RestController
public class UserController {

    @Autowired
     UserService userService;

    @GetMapping("/users")
    public ResponseResult users(HttpServletRequest request){


        //从http请求中 获取accessToken
        String accessToken = request.getHeader("Authorization");

        return  userService.getUserByAccessToken(accessToken);
    }
}

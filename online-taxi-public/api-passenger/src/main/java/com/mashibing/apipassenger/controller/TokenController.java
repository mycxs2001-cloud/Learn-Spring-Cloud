package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TokenService;
import org.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-09 22:24
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {
        String refreshTokenSrc = tokenResponse.getRefreshToken();
        System.out.println("原refreshToken : " + refreshTokenSrc);


        return tokenService.refreshToken(refreshTokenSrc);
    }
}

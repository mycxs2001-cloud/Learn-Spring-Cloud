package com.mashibing.servicepassengeruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-08 18:13
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service-passenger-user";
    }
}

package com.mashibing.apipassenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-05 11:47
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiPassengerApplication {

    //测试提交
    public static void main(String[] args) {
        SpringApplication.run(ApiPassengerApplication.class,args);
    }
}

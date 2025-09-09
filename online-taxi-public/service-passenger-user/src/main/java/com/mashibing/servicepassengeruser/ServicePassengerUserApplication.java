package com.mashibing.servicepassengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version: java version 1.8
 * @Author: cxs
 * @description:
 * @date: 2025-09-08 18:17
 */
@SpringBootApplication
@MapperScan("com.mashibing.servicepassengeruser.mapper")
public class ServicePassengerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePassengerUserApplication.class);
    }
}

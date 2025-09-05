package org.mashibing.serviceverifioncode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVerifioncodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVerifioncodeApplication.class, args);
    }

}

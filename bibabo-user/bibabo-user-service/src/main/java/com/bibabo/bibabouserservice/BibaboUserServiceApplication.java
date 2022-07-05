package com.bibabo.bibabouserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:22
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BibaboUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboUserServiceApplication.class);
    }
}

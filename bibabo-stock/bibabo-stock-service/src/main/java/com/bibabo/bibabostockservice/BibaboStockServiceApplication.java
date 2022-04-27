package com.bibabo.bibabostockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 14:39
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BibaboStockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboStockServiceApplication.class, args);
    }
}

package com.bibabo.bibabowdmservice;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 10:47
 * @description
 */
@SpringBootApplication
@EnableCanalClient
public class BibaboWdmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboWdmServiceApplication.class, args);
    }
}

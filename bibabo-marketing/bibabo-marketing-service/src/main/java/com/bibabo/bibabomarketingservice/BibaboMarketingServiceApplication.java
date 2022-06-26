package com.bibabo.bibabomarketingservice;

import com.bibabo.bibabomarketingservice.message.channel.OrderSink;
import com.bibabo.bibabomarketingservice.message.channel.OrderSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/26 12:09
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({OrderSink.class, OrderSource.class})
public class BibaboMarketingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboMarketingServiceApplication.class);
    }
}

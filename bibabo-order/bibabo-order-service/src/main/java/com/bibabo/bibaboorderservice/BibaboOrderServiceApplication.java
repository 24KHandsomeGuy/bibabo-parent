package com.bibabo.bibaboorderservice;

import com.bibabo.bibaboorderservice.channel.OrderSink;
import com.bibabo.bibaboorderservice.channel.SmsSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({OrderSink.class, SmsSource.class})
public class BibaboOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboOrderServiceApplication.class, args);
    }

}

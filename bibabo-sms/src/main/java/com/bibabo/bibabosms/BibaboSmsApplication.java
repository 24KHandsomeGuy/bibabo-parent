package com.bibabo.bibabosms;

import com.bibabo.bibabosms.channel.SmsSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(SmsSink.class)
public class BibaboSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboSmsApplication.class, args);
    }
}

package com.bibabo.bibabotrade;


import com.bibabo.bibabotrade.message.channel.SmsSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({SmsSource.class})
public class BibaboTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboTradeApplication.class, args);
    }
}

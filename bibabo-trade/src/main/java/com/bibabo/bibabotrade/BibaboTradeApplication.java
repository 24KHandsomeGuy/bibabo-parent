package com.bibabo.bibabotrade;


import com.bibabo.bibabotrade.message.channel.OrderSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBinding({OrderSource.class})
@EnableScheduling
public class BibaboTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibaboTradeApplication.class, args);
    }
}

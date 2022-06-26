package com.bibabo.bibabomarketingservice.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/26 12:49
 * @Description:
 */
@Service
@Slf4j
public class MarketingListener {

    @StreamListener("inputMarketing")
    public void receiveInputPaymentTimeOutCheckMsg(@Payload long orderId) {
        log.info("inputMarketing" + orderId);
    }
}

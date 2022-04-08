package com.bibabo.bibabosms.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 16:36
 * @description
 */
@Service
@Slf4j
public class ReceiveService {

    @StreamListener("inputCreateOrder2Sms")
    public void createOrder2SmsReceiveMsg(Message<?> message) {
        log.info(String.format("createOrder2SmsReceiveMsg receive msg %s", message));
    }
}

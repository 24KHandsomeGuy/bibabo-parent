package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.channel.SmsSource;
import com.bibabo.order.dto.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 16:41
 * @description
 */
@Service
@Slf4j
public class MessageSenderService {

    @Autowired
    private SmsSource smsSource;

    /**
     * 发送创建订单短信消息
     *
     * @param msg
     * @param <T>
     * @return
     */
    public <T> boolean sendCreateOrder2SmsMsg(OrderModel msg) {
        MessageBuilder builder = MessageBuilder.withPayload(msg).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        Message message = builder.build();
        return smsSource.outputCreateOrder2Sms().send(message);
    }
}

package com.bibabo.bibabomarketingservice.message.sender;

import com.bibabo.bibabomarketingservice.message.channel.OrderSource;
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
    private OrderSource orderSource;

    /**
     * 发送支付超时取消检查消息
     *
     * @param msg
     * @param <T>
     * @return
     */
    public <T> boolean sendMarketingMsg(Long msg) {
        MessageBuilder builder = MessageBuilder.withPayload(msg).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        Message message = builder.build();
        return orderSource.outputMarketing().send(message);
    }
}

package com.bibabo.bibabotrade.message.sender;

import com.alibaba.cloud.stream.binder.rocketmq.constant.RocketMQConst;
import com.bibabo.bibabotrade.message.channel.OrderSource;
import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
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
    private OrderSource createOrderSource;

    /**
     * 发送创建订单事务消息
     *
     * @param msg
     * @param bo
     * @param <T>
     * @return
     */
    public <T> boolean sendCreateOrderTransactionalMsg(Long msg, TransactionalMessageBO<T> bo) {
        MessageBuilder builder = MessageBuilder.withPayload(msg).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        // 事务obj key
        builder.setHeader(RocketMQConst.USER_TRANSACTIONAL_ARGS, bo);
        Message message = builder.build();
        return createOrderSource.outputCreateOrder().send(message);
    }
}

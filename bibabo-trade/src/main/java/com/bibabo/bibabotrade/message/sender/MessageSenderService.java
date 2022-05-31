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
 *
 * @see org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl#sendMessageInTransaction
 *          // ignore DelayTimeLevel parameter
 *         if (msg.getDelayTimeLevel() != 0) {
 *             MessageAccessor.clearProperty(msg, MessageConst.PROPERTY_DELAY_TIME_LEVEL);
 *         }
 * 由此可见事务消息发送会清除掉延时发送参数
 */
@Service
@Slf4j
public class MessageSenderService {

    @Autowired
    private OrderSource orderSource;

    /**
     * 发送订单支付事务消息
     *
     * @param msg
     * @param bo
     * @param <T>
     * @return
     */
    public <T> boolean sendPayedOrderTransactionalMsg(Long msg, TransactionalMessageBO<T> bo) {
        MessageBuilder builder = MessageBuilder.withPayload(msg).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        // 事务obj key
        builder.setHeader(RocketMQConst.USER_TRANSACTIONAL_ARGS, bo);
        Message message = builder.build();
        return orderSource.outputOrderPaid().send(message);
    }

    /**
     * 发送支付超时取消检查消息
     *
     * @param msg
     * @param <T>
     * @return
     */
    public <T> boolean sendPaymentTimeOutCheckMsg(Long msg) {
        MessageBuilder builder = MessageBuilder.withPayload(msg).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
        // delay key
        // builder.setHeader(RocketMQConst.PROPERTY_DELAY_TIME_LEVEL, 2);
        builder.setHeader(RocketMQConst.PROPERTY_DELAY_TIME_LEVEL, 4);
        Message message = builder.build();
        return orderSource.outputPaymentTimeOutCheck().send(message);
    }
}

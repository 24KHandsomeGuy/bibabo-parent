package com.bibabo.bibaboorderservice.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author fukuixiang
 * @date 2022/4/13
 * @time 17:19
 * @description
 */
public interface OrderSink {

    @Input("inputOrderPaid")
    SubscribableChannel inputOrderPaid();

    @Input("inputPaymentTimeOutCheck")
    SubscribableChannel inputPaymentTimeOutCheck();
}

package com.bibabo.bibabosms.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 16:31
 * @description
 */
public interface SmsSink {

    @Input("inputCreateOrder2Sms")
    SubscribableChannel inputCreateOrder2Sms();
}

package com.bibabo.bibabomarketingservice.message.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author fukuixiang
 * @date 2022/4/8
 * @time 16:17
 * @description
 */
public interface OrderSource {

    @Output("outputMarketing")
    MessageChannel outputMarketing();
}

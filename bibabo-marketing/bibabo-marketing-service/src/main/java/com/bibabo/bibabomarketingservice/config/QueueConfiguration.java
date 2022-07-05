package com.bibabo.bibabomarketingservice.config;

import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.ProcessorEnum;
import com.bibabo.bibabomarketingservice.model.enums.RedisRateLimiterEnum;
import com.bibabo.bibabomarketingservice.queue.QueueManager;
import com.bibabo.bibabomarketingservice.queue.QueueWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 17:15
 * @Description:
 */
@Configuration
public class QueueConfiguration {

    @Bean(name = "grantCouponQueueWorker", initMethod = "start", destroyMethod = "stop")
    public QueueWorker grantCouponQueueWorker() {
        QueueWorker queueWorker = new QueueWorker(ProcessorEnum.GRANT_COUPON.getName(), RedisRateLimiterEnum.QUEUE_GRANT_COUPONS);
        return queueWorker;
    }

    @Bean(name = "marketingActivityQueueWorker", initMethod = "start", destroyMethod = "stop")
    public QueueWorker marketingActivityQueueWorker() {
        QueueWorker queueWorker = new QueueWorker(ProcessorEnum.ACTIVITY.getName(), RedisRateLimiterEnum.QUEUE_ACTIVITY);
        return queueWorker;
    }

    @Bean
    public QueueManager<ActivityDTO> queueManager() {
        return new QueueManager<>();
    }
}

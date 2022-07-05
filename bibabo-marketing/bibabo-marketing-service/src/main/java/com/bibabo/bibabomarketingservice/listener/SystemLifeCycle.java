package com.bibabo.bibabomarketingservice.listener;

import com.bibabo.bibabomarketingservice.model.enums.RedisRateLimiterEnum;
import com.bibabo.bibabomarketingservice.common.SpringContext;
import com.bibabo.bibabomarketingservice.queue.QueueWorker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 12:03
 * @Description: 当前stop需要处于InputBindingLifecycle Integer.MAX_VALUE - 1000;、OutputBindingLifecycle Integer.MIN_VALUE + 1000;之间
 * 保证了当前节点不再接收任何消息，并且发送消息的channel还没有关闭，可以发送到rocketmq上
 */
@Slf4j
@Component
public class SystemLifeCycle implements SmartLifecycle {

    @Autowired
    private RedissonClient redissonClient;

    private volatile boolean running;

    @Override
    public void start() {
        if (!running) {
            log.info("SystemLifeCycle start");
            for (RedisRateLimiterEnum value : RedisRateLimiterEnum.values()) {
                RRateLimiter rRateLimiter = redissonClient.getRateLimiter(value.getResource());
                boolean trySetResult = rRateLimiter.trySetRate(value.getRateType(),
                        value.getRate(),
                        value.getRateInterval(),
                        value.getRateIntervalUnit());
                log.info("reasource redis key:{}, try set rate result:{}", value.getResource(), trySetResult);
            }
            running = true;
        }
    }

    @Override
    public void stop() {
        if (running) {
            log.info("SystemLifeCycle stop");
            // 把queue中剩余的元素作为消息发送到rocketmq中。tag作为类型区分是一层队列缓冲"活动决策"是否通过、还是二层队列缓冲"营销资产系统"承载能力
            SpringContext.springClosed();
            List<QueueWorker> queueWorkerList = SpringContext.getBeansOfType(QueueWorker.class);
            for (QueueWorker queueWorker : queueWorkerList) {
                queueWorker.drainToRocketMq();
            }
            running = false;
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return 1000;
    }
}

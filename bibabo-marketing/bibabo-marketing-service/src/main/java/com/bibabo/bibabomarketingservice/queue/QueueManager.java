package com.bibabo.bibabomarketingservice.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 14:59
 * @Description:
 */
@Slf4j
public class QueueManager<T> {

    private final Map<String, BlockingQueue<T>> BLOCKING_QUEUE_MAP = new ConcurrentHashMap<>();

    public BlockingQueue<T> getBlockingQueue(String queueName) {
        if (BLOCKING_QUEUE_MAP.get(queueName) != null) {
            return BLOCKING_QUEUE_MAP.get(queueName);
        }
        BLOCKING_QUEUE_MAP.putIfAbsent(queueName, new LinkedBlockingQueue<T>());
        return BLOCKING_QUEUE_MAP.get(queueName);
    }
}

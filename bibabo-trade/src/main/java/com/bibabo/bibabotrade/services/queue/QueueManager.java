package com.bibabo.bibabotrade.services.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author fukuixiang
 * @date 2022/6/10
 * @time 18:46
 * @description
 */
@Slf4j
public abstract class QueueManager {

    private static final BlockingQueue<String> REPORT_LOG_QUEUE = new LinkedBlockingQueue<>();

    public static BlockingQueue<String> getReportLogQueue() {
        return REPORT_LOG_QUEUE;
    }
}

package com.bibabo.bibabotrade.services.scheduled;

import com.bibabo.bibabotrade.services.queue.QueueManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author fukuixiang
 * @date 2022/6/10
 * @time 18:43
 * @description
 */
@Service
@Slf4j
public class LogSchedule {

    @Scheduled(cron = "0/10 * * * * ?")
    public void reportLogRecord2ES() {
        String element;
        while ((element = QueueManager.getReportLogQueue().poll()) != null) {
            log.info("LogSchedule抓取创建订单日志上报ES:{}", element);
        }
    }
}

package com.bibabo.bibabotrade.services.scheduled;

import com.bibabo.bibabotrade.services.queue.QueueManager;
import com.bibabo.utils.contant.Contants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author fukuixiang
 * @date 2022/6/10
 * @time 18:43
 * @description
 */
@Service
@Slf4j
public class LogSchedule {


    @Autowired
    RestHighLevelClient restHighLevelClient;


    @SneakyThrows
    @Scheduled(cron = "0/10 * * * * ?")
    public void reportLogRecord2ES() {
        Object element;
        while ((element = QueueManager.getReportLogQueue().poll()) != null) {
            if (!(element instanceof Serializable)) {
                continue;
            }
            String json = new ObjectMapper().writeValueAsString(element);
            log.info("LogSchedule抓取创建订单日志上报ES:{}", json);
            IndexRequest indexRequest = new IndexRequest(Contants.ES_BIBABO_LOG_INDEX);
            indexRequest.source(json, XContentType.JSON);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }
    }
}

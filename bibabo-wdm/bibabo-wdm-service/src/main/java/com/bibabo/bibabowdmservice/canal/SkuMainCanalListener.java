package com.bibabo.bibabowdmservice.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.bibabo.bibabowdmservice.domain.SkuMain;
import com.bibabo.bibabowdmservice.model.enums.ElasticsearchIndexEnum;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author fukuixiang
 * @date 2022/6/16
 * @time 10:06
 * @description
 */
@CanalEventListener
@Slf4j
public class SkuMainCanalListener {

    @Autowired
    RestHighLevelClient restHighLevelClient;


    @ListenPoint(destination = "bibabo", schema = {"bibabo_wdm"}, table = {"sku_main"}, eventType = {CanalEntry.EventType.INSERT, CanalEntry.EventType.UPDATE, CanalEntry.EventType.DELETE})
    public void onSkuMainEventPut2Es(CanalEntry.EventType eventType, CanalEntry.RowData rowData) throws Exception {
        log.info("onSkuMainEventPut2Es eventType:{}", eventType);
        if (eventType == CanalEntry.EventType.INSERT || eventType == CanalEntry.EventType.UPDATE) {
            IndexRequest indexRequest = new IndexRequest(ElasticsearchIndexEnum.SKU.getIndex());
            String skuId = rowData.getAfterColumns(0).getValue();
            indexRequest = indexRequest.id(skuId);
            StringBuilder skuMainJson = new StringBuilder("{");
            int j = 0;
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                // 不考虑布尔
                boolean nextCharToUpper = false;
                StringBuilder fieldName = new StringBuilder();
                int i = 0;
                for (int c : column.getName().toCharArray()) {
                    if (i++ == 0) {
                        fieldName.append((char) c);
                        continue;
                    }
                    if ("_".charAt(0) == c) {
                        nextCharToUpper = true;
                        continue;
                    }
                    int insertChar = c;
                    if (nextCharToUpper) {
                        insertChar = c - 32;
                        nextCharToUpper = false;
                    }
                    fieldName.append((char) insertChar);
                }
                skuMainJson.append("\"").append(fieldName).append("\"").append(":");
                Class fieldClazz = SkuMain.class.getDeclaredField(fieldName.toString()).getType();
                if ("java.lang.String".equals(fieldClazz.getName())) {
                    skuMainJson.append("\"").append(column.getValue()).append("\"");
                } else if ("java.util.Date".equals(fieldClazz.getName())) {
                    Date date = DateUtils.parseDate(column.getValue(), "yyyy-MM-dd HH:mm:ss");
                    skuMainJson.append(date.getTime());
                } else {
                    skuMainJson.append(column.getValue());
                }
                if (j++ == rowData.getAfterColumnsCount() - 1) {
                    skuMainJson.append("}");
                } else {
                    skuMainJson.append(",");
                }
            }

            indexRequest.source(skuMainJson.toString(), XContentType.JSON);
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("onSkuMainEventPut2Es skuId:{} put document to ES successfully", skuId);
        } else if (eventType == CanalEntry.EventType.DELETE) {
            DeleteRequest deleteRequest = new DeleteRequest(ElasticsearchIndexEnum.SKU.getIndex());
            String skuId = rowData.getBeforeColumns(0).getValue();
            deleteRequest.id(skuId);
            DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("onSkuMainEventPut2Es skuId:{} delete ES document successfully", skuId);
        } else {
            // do nothing
        }
    }

}

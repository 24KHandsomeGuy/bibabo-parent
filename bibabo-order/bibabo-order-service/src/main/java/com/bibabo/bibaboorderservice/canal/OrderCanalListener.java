package com.bibabo.bibaboorderservice.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.bibabo.bibaboorderservice.model.enums.RedisPrefixEnum;
import com.bibabo.bibaboorderservice.services.impl.RedisCommonProcessor;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fukuixiang
 * @date 2022/6/8
 * @time 14:36
 * @description
 */
@CanalEventListener
@Slf4j
public class OrderCanalListener {

    private static final String ORDER_ADDRESS_COLUMN = "cust_address";

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    /**
     * 订单修改地址事件
     */
    @ListenPoint(destination = "bibabo", schema = "bibabo_order", table = {"order_main"}, eventType = CanalEntry.EventType.UPDATE)
    public void onUpdateOrderAddressEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        log.info("OrderCanalListener on event onUpdateOrderAddressEvent orderId {}", rowData.getAfterColumns(1).getValue());
        rowData.getAfterColumnsList()
                .stream()
                .filter(CanalEntry.Column::getUpdated)
                .filter(column -> ORDER_ADDRESS_COLUMN.equals(column.getName()))
                .forEach(column -> {
                    String orderId = rowData.getAfterColumns(1).getValue();
                    String redisKey = RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.ORDER_ADDRESS, orderId);
                    boolean rst = redisCommonProcessor.deleteRedisKey(redisKey);
                    log.info("delete key:{}, result: {}", redisKey, rst);
                });
    }

}

package com.bibabo.bibaboorderservice.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.event.CanalEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author fukuixiang
 * @date 2022/6/8
 * @time 17:03
 * @description
 */
@Slf4j
@Component
public class PrintCanalLogListener implements CanalEventListener {

    @Override
    public void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        // log.info("Canal PrintCanalLogListener onEvent, eventType:{}", eventType);
    }
}

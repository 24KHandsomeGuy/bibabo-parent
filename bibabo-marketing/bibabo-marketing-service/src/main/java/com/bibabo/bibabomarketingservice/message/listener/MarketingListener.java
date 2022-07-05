package com.bibabo.bibabomarketingservice.message.listener;

import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.MessageTypeEnum;
import com.bibabo.bibabomarketingservice.queue.QueueManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/26 12:49
 * @Description:
 */
@Service
@Slf4j
public class MarketingListener {

    @Autowired
    QueueManager<ActivityDTO> queueManager;

    @StreamListener("inputMarketing")
    public void receiveInputPaymentTimeOutCheckMsg(@Payload ActivityDTO activityDTO, @Headers Map<String, Object> messageHeader) {
        log.info("inputMarketing:{}", activityDTO);
        /*int type = Integer.parseInt(String.valueOf(messageHeader.get(MessageConst.PROPERTY_TAGS)));*/
        String processorName = MessageTypeEnum.findProcessNameByType(activityDTO.getType());
        if (StringUtils.isNotBlank(processorName)) {
            boolean rst = queueManager.getBlockingQueue(processorName).offer(activityDTO);
            log.info("inputMarketing:{}, offer to queue manager:{}, result:{}", activityDTO, processorName, rst);
        }
    }

}

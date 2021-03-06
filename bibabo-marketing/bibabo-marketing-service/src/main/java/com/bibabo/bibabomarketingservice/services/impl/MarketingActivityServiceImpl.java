package com.bibabo.bibabomarketingservice.services.impl;

import com.bibabo.bibabomarketingservice.domain.GiftCustRecord;
import com.bibabo.bibabomarketingservice.domain.repository.GiftCustRecordRepository;
import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.GiftStatusEnum;
import com.bibabo.bibabomarketingservice.model.enums.ProcessorEnum;
import com.bibabo.bibabomarketingservice.queue.QueueManager;
import com.bibabo.marketing.dto.JoinActivityRequestDTO;
import com.bibabo.marketing.services.MarketingActivityServiceI;
import com.bibabo.utils.model.RpcResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 10:32
 * @Description:
 */
@Slf4j
@DubboService
public class MarketingActivityServiceImpl implements MarketingActivityServiceI {

    @Autowired
    private QueueManager<ActivityDTO> queueManager;

    @Autowired
    private GiftCustRecordRepository giftCustRecordRepository;

    @Override
    public RpcResponseDTO<Boolean> joinActivity(JoinActivityRequestDTO dto) {

        if (dto == null || dto.getActivityId() == null || dto.getCustId() == null) {
            return RpcResponseDTO.<Boolean>builder().fail("入参为空").build();
        }

        // 落库
        GiftCustRecord giftCustRecord = GiftCustRecord.builder()
                .custId(dto.getCustId())
                .activityId(dto.getActivityId())
                .giftStatus(GiftStatusEnum.NEW.getStatus())
                .createDate(new Date())
                .build();
        GiftCustRecord giftCustRecordDB = giftCustRecordRepository.save(giftCustRecord);

        // 发到本地queue中
        boolean rst = queueManager.getBlockingQueue(ProcessorEnum.ACTIVITY.getName()).offer(ActivityDTO.builder().activityId(dto.getActivityId()).custId(dto.getCustId()).giftRecordId(giftCustRecordDB.getId()).build());
        log.info("activity Id:{}, cust id:{}, push to local queue:{}, result:{}", dto.getActivityId(), dto.getCustId(), ProcessorEnum.ACTIVITY.getName(), rst);
        return RpcResponseDTO.<Boolean>builder().success(rst).build();
    }
}

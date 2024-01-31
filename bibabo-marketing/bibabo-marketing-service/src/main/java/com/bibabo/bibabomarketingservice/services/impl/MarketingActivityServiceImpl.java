package com.bibabo.bibabomarketingservice.services.impl;

import com.bibabo.bibabomarketingservice.domain.GiftCustRecord;
import com.bibabo.bibabomarketingservice.domain.repository.GiftCustRecordRepository;
import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.GiftStatusEnum;
import com.bibabo.bibabomarketingservice.model.enums.ProcessorEnum;
import com.bibabo.bibabomarketingservice.queue.QueueManager;
import com.bibabo.marketing.dto.JoinActivityRequestDTO;
import com.bibabo.marketing.services.MarketingActivityServiceI;
import com.bibabo.utils.model.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 10:32
 * @Description:
 */
@Slf4j
@DubboService(protocol = {"dubbo", "rest"})
@Path("/")
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
@Consumes({MediaType.APPLICATION_JSON})
public class MarketingActivityServiceImpl implements MarketingActivityServiceI {

    @Autowired
    private QueueManager<ActivityDTO> queueManager;

    @Autowired
    private GiftCustRecordRepository giftCustRecordRepository;

    @Override
    @Path("activity")
    @POST
    public ResponseDTO<Boolean> joinActivity(JoinActivityRequestDTO dto) {

        if (dto == null || dto.getActivityId() == null || dto.getCustId() == null) {
            return ResponseDTO.<Boolean>builder().fail("入参为空").build();
        }

        // 落库
        GiftCustRecord giftCustRecord = GiftCustRecord.builder()
                .custId(dto.getCustId())
                .activityId(dto.getActivityId())
                .giftStatus(GiftStatusEnum.NEW.getStatus())
                .createDate(new Date())
                .build();
        // 唯一键冲突异常捕获，返回失败
        GiftCustRecord giftCustRecordDB = null;
        try {
            giftCustRecordDB = giftCustRecordRepository.save(giftCustRecord);
        } catch (Exception e) {
            log.error("", e);
            return ResponseDTO.<Boolean>builder().fail("custId：" + dto.getCustId() + " has joined activityId：" + dto.getActivityId()).build();
        }

        // 发到本地queue中
        boolean rst = queueManager.getBlockingQueue(ProcessorEnum.ACTIVITY.getName()).offer(ActivityDTO.builder().activityId(dto.getActivityId()).custId(dto.getCustId()).giftRecordId(giftCustRecordDB.getId()).build());
        log.info("activity Id:{}, cust id:{}, push to local queue:{}, result:{}", dto.getActivityId(), dto.getCustId(), ProcessorEnum.ACTIVITY.getName(), rst);
        return ResponseDTO.<Boolean>builder().success(rst).build();
    }
}

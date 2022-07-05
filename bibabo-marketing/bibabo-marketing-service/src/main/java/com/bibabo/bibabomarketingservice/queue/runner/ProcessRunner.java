package com.bibabo.bibabomarketingservice.queue.runner;

import com.bibabo.bibabomarketingservice.domain.repository.GiftActivityRepository;
import com.bibabo.bibabomarketingservice.domain.repository.GiftCustRecordRepository;
import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.GiftStatusEnum;
import com.bibabo.bibabomarketingservice.model.enums.ProcessorEnum;
import com.bibabo.bibabomarketingservice.queue.QueueManager;
import com.bibabo.bibabomarketingservice.queue.strategy.DispatchResult;
import com.bibabo.bibabomarketingservice.queue.strategy.DispatchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 16:08
 * @Description:
 */
@Slf4j
public abstract class ProcessRunner extends BaseRunner {

    @Autowired
    protected GiftActivityRepository giftActivityRepository;

    @Autowired
    protected GiftCustRecordRepository giftCustRecordRepository;

    @Autowired
    private QueueManager<ActivityDTO> queueManager;

    @Override
    public void doWork() {
        DispatchStrategy dispatchStrategy = doProcess();
        DispatchResult dispatchResult = dispatchStrategy.dispatch();
        ActivityDTO activityDTO = (ActivityDTO) getElement();
        Optional.ofNullable(dispatchResult).ifPresent(result -> {
            if (result.getNextStatus() >= 0) {
                int updResult = giftCustRecordRepository.updateGiftCustRecordStatus(activityDTO.getGiftRecordId(), result.getNextStatus());
                log.info("gift record id:{}, update status to {}, result:{}", activityDTO.getGiftRecordId(), result.getNextStatus(), updResult);
                if (updResult <= 0) {
                    return;
                }
            }
            if (result.getIsFinish() != 1) {
                String processorName = ProcessorEnum.getProcessorName(result.getNextStatus());
                if (StringUtils.isNotBlank(processorName)) {
                    boolean rst = queueManager.getBlockingQueue(processorName).offer(activityDTO);
                    log.info("activity Id:{}, cust id:{}, push to local queue:{}, result:{}", activityDTO.getActivityId(), activityDTO.getCustId(), processorName, rst);
                }
            }
        });
    }

    protected abstract DispatchStrategy doProcess();
}

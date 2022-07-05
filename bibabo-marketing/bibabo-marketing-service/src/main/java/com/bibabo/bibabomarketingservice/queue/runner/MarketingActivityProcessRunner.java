package com.bibabo.bibabomarketingservice.queue.runner;

import com.bibabo.bibabomarketingservice.domain.GiftActivity;
import com.bibabo.bibabomarketingservice.domain.GiftCondition;
import com.bibabo.bibabomarketingservice.domain.GiftCustRecord;
import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.GiftStatusEnum;
import com.bibabo.bibabomarketingservice.queue.strategy.DispatchStrategy;
import com.bibabo.bibabomarketingservice.queue.strategy.MarketingActivityDispatchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 17:07
 * @Description:
 */
@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MarketingActivityProcessRunner extends ProcessRunner {

    @Override
    protected DispatchStrategy doProcess() {

        DispatchStrategy strategy = new MarketingActivityDispatchStrategy(-1);
        try {
            // age需要调用user系统获取
            int age = 16;

            ActivityDTO activityDTO = (ActivityDTO) getElement();
            GiftCustRecord giftCustRecord = giftCustRecordRepository.findByActivityIdAndCustIdAndGiftStatus(activityDTO.getActivityId(), activityDTO.getCustId(), GiftStatusEnum.NEW.getStatus());
            GiftActivity giftActivity = giftActivityRepository.findByActivityIdAndActivityStatus(activityDTO.getActivityId(), 1);

            boolean isCustomerSuitActivity = false;
            if (CollectionUtils.isNotEmpty(giftActivity.getGiftConditions())) {
                Date now = new Date();
                for (GiftCondition condition : giftActivity.getGiftConditions()) {
                    if (condition.getDelFlag() != 1
                            && condition.getStartDate() != null
                            && condition.getEndDate() != null
                            && now.getTime() > condition.getStartDate().getTime()
                            && now.getTime() < condition.getEndDate().getTime()) {
                        String lower = condition.getLower();
                        boolean isSuitLower = StringUtils.isBlank(lower) ? true : age > Integer.parseInt(lower);
                        String upper = condition.getUpper();
                        boolean isSuitUpper = StringUtils.isBlank(upper) ? true : age < Integer.parseInt(upper);
                        if (isSuitLower && isSuitUpper) {
                            isCustomerSuitActivity = true;
                            break;
                        }
                    }
                }
            }

            if (isCustomerSuitActivity) {
                log.info("cust id:{} is suit activity Id:{}, allow to join", activityDTO.getCustId(), activityDTO.getActivityId());
                // 满足活动 修改活动记录状态为满足活
                activityDTO.setCouponId(giftActivity.getCouponId());
                strategy = new MarketingActivityDispatchStrategy(1);
            } else {
                // 不满足活动
                log.info("cust id:{} is not suit activity Id:{}, not allow to join", activityDTO.getCustId(), activityDTO.getActivityId());
                strategy = new MarketingActivityDispatchStrategy(2);
            }
        } catch (RuntimeException e) {
            log.error("MarketingActivityProcessRunner doProcess error:{}", e.getMessage(), e);
        }

        return strategy;
    }
}

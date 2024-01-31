package com.bibabo.bibabomarketingservice.queue.runner;

import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.queue.strategy.DispatchStrategy;
import com.bibabo.bibabomarketingservice.queue.strategy.GrantCouponDispatchStrategy;
import com.bibabo.user.dto.GrantCouponRequestDTO;
import com.bibabo.user.services.GrantCouponService;
import com.bibabo.utils.model.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 17:07
 * @Description:
 */
@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GrantCouponProcessRunner extends ProcessRunner {

    @DubboReference(check = false, timeout = 30 * 1000)
    private GrantCouponService grantCouponService;

    @Override
    protected DispatchStrategy doProcess() {
        log.info("GrantCouponProcessRunner receive element:{}", element);
        ActivityDTO activityDTO = (ActivityDTO) getElement();
        GrantCouponRequestDTO requestDTO = new GrantCouponRequestDTO(activityDTO.getCouponId(), activityDTO.getCustId());
        ResponseDTO responseDTO = grantCouponService.grantCoupon(requestDTO);
        if (responseDTO.getSuccess()) {
            log.info("cust id:{} is suit activity Id:{}, grant coupon id:{} success", activityDTO.getCustId(), activityDTO.getActivityId(), activityDTO.getCouponId());
            // 发劵成功
            return new GrantCouponDispatchStrategy(1);
        } else {
            // 发劵失败
            log.info("cust id:{} is suit activity Id:{}, grant coupon id:{} faild, reasson:{}", activityDTO.getCustId(), activityDTO.getActivityId(), activityDTO.getCouponId(), responseDTO.getMessage());
            return new GrantCouponDispatchStrategy(2);
        }
    }
}

package com.bibabo.bibaboorderservice.services.timewheel;

import com.bibabo.bibaboorderservice.domain.OrderMain;
import com.bibabo.bibaboorderservice.domain.repository.OrderMainRepository;
import com.bibabo.bibaboorderservice.model.enums.OrderStatusEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.timer.Timeout;
import org.apache.dubbo.common.timer.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author fukuixiang
 * @date 2022/6/13
 * @time 14:45
 * @description
 */
@Setter
@Slf4j
@Service
@Scope("prototype")
public class GrantOverTimeCouponTask implements TimerTask {

    private Long orderId;

    private Long custId;

    @Autowired
    private OrderMainRepository orderMainRepository;


    @Override
    public void run(Timeout timeout) throws Exception {
        if (timeout.isCancelled() || timeout.timer().isStop()) {
            return;
        }
        OrderMain orderMain = orderMainRepository.findByOrderId(orderId);
        Optional.ofNullable(orderMain).ifPresent(om -> {
            // 如果状态还未妥投，则调用用户系统，发放超时补偿劵
            if (om.getOrderStatus() < OrderStatusEnum.TMS_ACHIEVEMENT.getStatus()) {
                log.info("调用用户系统发放超时补偿劵，超时订单:{}，发劵用户:{}", orderId, custId);
            } else {
                log.info("订单:{}，发劵用户:{}，已经妥投，无需发劵", orderId, custId);
            }
        });

    }
}

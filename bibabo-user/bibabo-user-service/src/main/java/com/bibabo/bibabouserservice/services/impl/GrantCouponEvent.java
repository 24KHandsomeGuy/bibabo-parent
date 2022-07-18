package com.bibabo.bibabouserservice.services.impl;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/18 10:16
 * @Description:
 */
@Data
public class GrantCouponEvent extends ApplicationEvent {

    private Long couponId;

    private Long custId;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public GrantCouponEvent(Object source, long custId, long couponId) {
        super(source);
        this.couponId = couponId;
        this.custId = custId;
    }
}

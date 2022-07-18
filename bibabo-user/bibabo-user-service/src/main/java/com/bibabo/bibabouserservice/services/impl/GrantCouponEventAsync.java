package com.bibabo.bibabouserservice.services.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/18 10:16
 * @Description:
 */
@Data
@AllArgsConstructor
public class GrantCouponEventAsync {

    private Long couponId;

    private Long custId;
}

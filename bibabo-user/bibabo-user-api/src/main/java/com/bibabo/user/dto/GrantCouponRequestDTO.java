package com.bibabo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:55
 * @Description:
 */
@Data
@AllArgsConstructor
public class GrantCouponRequestDTO implements Serializable {

    private Long couponId;

    private Long custId;
}

package com.bibabo.user.services;

import com.bibabo.user.dto.GrantCouponRequestDTO;
import com.bibabo.utils.model.ResponseDTO;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:56
 * @Description:
 */
public interface GrantCouponService {
    
    ResponseDTO grantCoupon(GrantCouponRequestDTO dto);
}

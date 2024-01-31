package com.bibabo.bibabotrade.services;

import com.bibabo.marketing.dto.JoinActivityRequestDTO;
import com.bibabo.utils.model.ResponseDTO;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 17:41
 * @Description:
 */
public interface MarketingServicel {

    ResponseDTO<Boolean> joinActivity(JoinActivityRequestDTO requestDTO);
}

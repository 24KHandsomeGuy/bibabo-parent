package com.bibabo.marketing.services;

import com.bibabo.marketing.dto.JoinActivityRequestDTO;
import com.bibabo.utils.model.RpcResponseDTO;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 19:20
 * @Description:
 */
public interface MarketingActivityServiceI {

    /**
     * 用户参加活动
     *
     * @param dto
     * @return
     */
    RpcResponseDTO<Boolean> joinActivity(JoinActivityRequestDTO dto);
}

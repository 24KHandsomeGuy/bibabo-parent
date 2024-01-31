package com.bibabo.signin.service.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/31 16:25
 * @Description
 */
@Data
@AllArgsConstructor
public class RewardInfo {

    /**
     * 奖励类型（1：优惠券， 2：积分， 3:令牌, 4:庄园金币, 5：庄园肥料, 6:果园水滴) 参考CheckInRewardEnum
     */
    private Integer type;

    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励数量
     */
    private Integer count;
}

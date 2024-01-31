package com.bibabo.signin.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 13:43
 * @Description 奖励明细
 */
@Data
@AllArgsConstructor
public class RewardInfoVO implements java.io.Serializable {

    private static final long serialVersionUID = 4348601004737845212L;

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

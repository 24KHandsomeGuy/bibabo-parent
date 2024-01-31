package com.bibabo.signin.service.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 13:40
 * @Description
 */
@Data
@NoArgsConstructor
public class SignInVO implements java.io.Serializable {

    private static final long serialVersionUID = -7813821085175740618L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 当日签到排名
     */
    private Long dailyRanking;

    /**
     * 奖励列表
     */
    private List<RewardInfoVO> rewardInfoList;

    /**
     * 连续签到次数
     */
    private Integer continuousCheckInTimes;

}

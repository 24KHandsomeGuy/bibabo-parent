package com.bibabo.bibabomarketingservice.model.dto;

import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 18:29
 * @Description:
 */
@Data
public class ActivityDTO extends BaseDTO {

    private Long activityId;

    private Long userId;

    private Long couponId;

    private String skuIds;
}

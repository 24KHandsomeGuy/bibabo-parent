package com.bibabo.bibabomarketingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 18:29
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO extends BaseDTO {

    private Long activityId;

    private Long custId;

    private Long couponId;

    private Integer type;

    private Long giftRecordId;
}

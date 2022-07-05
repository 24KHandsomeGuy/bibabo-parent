package com.bibabo.marketing.dto;

import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 10:26
 * @Description:
 */
@Data
public class JoinActivityRequestDTO extends BaseDTO {

    private Long activityId;

    private Long custId;
}

package com.bibabo.wdm.dto;

import lombok.Data;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 11:00
 * @description
 */
@Data
public class SkuQueryRequest extends BaseDTO {

    private Long skuId;

    private String skuName;

    private Integer from;

    private Integer size;
}

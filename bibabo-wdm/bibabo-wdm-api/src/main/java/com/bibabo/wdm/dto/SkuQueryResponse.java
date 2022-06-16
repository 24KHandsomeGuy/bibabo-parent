package com.bibabo.wdm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 11:00
 * @description
 */
@Data
public class SkuQueryResponse extends BaseDTO {

    private Long skuId;

    private String skuName;

    private Integer skuType;

    private String upcNo;

    private BigDecimal salePrice;

    private BigDecimal price;

    private Date createDate;
}

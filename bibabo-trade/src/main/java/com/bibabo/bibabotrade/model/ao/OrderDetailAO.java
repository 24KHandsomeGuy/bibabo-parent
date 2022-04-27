package com.bibabo.bibabotrade.model.ao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 18:13
 * @Description:
 */
@Data
@NoArgsConstructor
public class OrderDetailAO implements Serializable {

    /**
     * 商品编码 商品主数据sku_main主键
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 商品数量
     */
    private Integer skuNum;
/*
    *//**
     * 商品标价
     *//*
    @NotNull
    private BigDecimal skuPrice;

    *//**
     * 实际售价
     *//*
    @NotNull
    private BigDecimal salePirce;*/

    private Integer wareId;
}

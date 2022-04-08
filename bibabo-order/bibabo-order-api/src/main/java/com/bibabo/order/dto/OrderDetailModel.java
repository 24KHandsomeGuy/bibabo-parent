package com.bibabo.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author fukuixiang
 * @date 2022/4/8
 * @time 18:27
 * @description
 */
@Data
public class OrderDetailModel extends BaseDTO {

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

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 商品价
     */
    private BigDecimal skuPrice;
}

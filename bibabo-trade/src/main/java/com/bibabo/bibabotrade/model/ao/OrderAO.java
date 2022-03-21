package com.bibabo.bibabotrade.model.ao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 17:53
 * @Description:
 */
@Data
@NoArgsConstructor
public class OrderAO {

    private List<OrderDetailAO> orderDetailAOList;

    /**
     * 顾客id 用户系统主键
     */
    private String custId;

    /**
     * 期望收货日期
     *//*
    private Date bookDate;

    *//**
     * 顾客电话
     *//*
    private String custTelephone;

    *//**
     * 收货省
     *//*
    private String provinceName;

    *//**
     * 收货市
     *//*
    private String cityName;

    *//**
     * 收货区、县
     *//*
    private String county;

    *//**
     * 地址详情
     *//*
    private String address;

    *//**
     * 实际支付总价
     *//*
    private BigDecimal payMoney;

    *//**
     * 商品总价
     *//*
    private BigDecimal totalPrice;

    *//**
     * 优惠价格
     *//*
    private BigDecimal couponPrice;*/
}

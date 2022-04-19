package com.bibabo.order.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/8
 * @time 18:05
 * @description
 */
@Data
public class OrderModel extends BaseDTO {

    private Long orderId;

    private List<OrderDetailModel> orderDetailModelList;

    /**
     * 顾客id 用户系统主键
     */
    private String custId;


    private Date createDate;

    /**
     * 顾客电话
     */
    private String custTelephone;

    /**
     * 顾客地址
     */
    private String address;
}

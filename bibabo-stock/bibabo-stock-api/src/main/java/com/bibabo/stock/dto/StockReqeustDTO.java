package com.bibabo.stock.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:19
 * @description
 */
@Data
public class StockReqeustDTO extends BaseDTO {

    private Long orderId;

    private Long skuId;

    private Integer skuNum;

    private Integer wareId;
}

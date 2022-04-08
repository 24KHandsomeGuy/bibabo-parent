package com.bibabo.bibabotrade.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author fukuixiang
 * @date 2022/4/8
 * @time 15:59
 * @description
 */
@Builder
@Data
public class CreateOrderMessageDTO {

    private Long orderId;

    private Date createDate;

    private String custTelephone;
}

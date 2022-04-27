package com.bibabo.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:32
 * @description
 */
@Data
@AllArgsConstructor
public class StockResponseDTO extends BaseDTO {

    private Long skuId;

    private Integer wareId;

    private Integer stockNum;
}

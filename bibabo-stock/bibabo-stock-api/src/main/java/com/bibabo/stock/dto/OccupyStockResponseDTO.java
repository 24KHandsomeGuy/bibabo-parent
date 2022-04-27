package com.bibabo.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:20
 * @description
 */
@Data
@AllArgsConstructor
public class OccupyStockResponseDTO extends BaseDTO {

    /**
     * 响应状态  1 成功  -1失败
     */
    private Integer rtnStatus;
    /**
     * 响应信息。失败会有信息
     */
    private String rtnMsg;
}

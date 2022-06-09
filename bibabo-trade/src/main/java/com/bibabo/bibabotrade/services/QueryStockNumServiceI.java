package com.bibabo.bibabotrade.services;

import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/6/9
 * @time 14:37
 * @description
 */
public interface QueryStockNumServiceI {

    List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList);
}

package com.bibabo.stock.services;

import com.bibabo.stock.dto.OccupyStockResponseDTO;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:04
 * @description
 */
public interface StockServiceI {

    /**
     * 占用库存
     *
     * @param reqeustDTOList
     * @return
     */
    OccupyStockResponseDTO occupyStock(List<StockReqeustDTO> reqeustDTOList);

    /**
     * 查询库存，批量
     *
     * @param reqeustDTOList
     * @return
     */
    List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList);
}

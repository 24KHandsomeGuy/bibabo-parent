package com.bibabo.bibabostockservice.services.impl;

import com.bibabo.bibabostockservice.services.StockInventoryService;
import com.bibabo.stock.dto.OccupyStockResponseDTO;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;
import com.bibabo.stock.services.StockServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:35
 * @description
 */
@Slf4j
@DubboService
public class StockServiceDbImpl implements StockServiceI {

    @Autowired
    private StockInventoryService stockInventoryService;

    @Override
    public OccupyStockResponseDTO occupyStock(List<StockReqeustDTO> reqeustDTOList) {
        String errorMsg = null;
        try {
            stockInventoryService.batchOccupyStock(reqeustDTOList);
        } catch (RuntimeException e) {
            errorMsg = e.getMessage();
        }
        if (errorMsg != null) {
            return new OccupyStockResponseDTO(-1, errorMsg);
        }
        return new OccupyStockResponseDTO(1, "订单占用库存成功");
    }

    @Override
    public List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList) {
        return null;
    }
}

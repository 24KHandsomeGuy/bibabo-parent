package com.bibabo.bibabostockservice.services;

import com.bibabo.bibabostockservice.domain.StockInventory;
import com.bibabo.stock.dto.StockReqeustDTO;

import java.util.List;

/**
 * @Author: Damon Fu
 * @Date: 2022/5/28 21:32
 * @Description:
 */
public interface StockInventoryService {

    int batchOccupyStock(List<StockReqeustDTO> reqeustDTOList);

    StockInventory findBySkuId(long skuId);
}

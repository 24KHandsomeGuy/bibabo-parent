package com.bibabo.bibabostockservice.services.impl;

import com.bibabo.bibabostockservice.domain.StockInventory;
import com.bibabo.bibabostockservice.domain.repository.StockInventoryRepository;
import com.bibabo.bibabostockservice.services.StockInventoryService;
import com.bibabo.stock.dto.StockReqeustDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Damon Fu
 * @Date: 2022/5/28 21:37
 * @Description:
 */
@Service
public class StockInventoryServiceImpl implements StockInventoryService {

    @Autowired
    StockInventoryRepository stockInventoryRepository;

    @Override
    @Transactional
    public int batchOccupyStock(List<StockReqeustDTO> reqeustDTOList) {
        // 应该是批量操作， 并发也需要考虑
        for (StockReqeustDTO dto : reqeustDTOList) {
            // FIXME 同一个条件查询，修改后，居然没读到最新值，怀疑有缓存！！！很变态，后续可以解决下
            StockInventory stockInventory = stockInventoryRepository.findBySkuIdAndWareId(dto.getSkuId(), dto.getWareId());
            BigDecimal availableInventory = stockInventory.getStockNum().subtract(stockInventory.getOrderBookNum());
            BigDecimal remainingInventory = availableInventory.subtract(new BigDecimal(dto.getSkuNum()));
            if (remainingInventory.compareTo(BigDecimal.ZERO) < 0) {
                String errMsg = String.format("skuId:%d,库存不足", dto.getSkuId());
                throw new RuntimeException(errMsg);
            }
            // 增加占用数
            int rst = stockInventoryRepository.setOrderBookNumFor(stockInventory.getId(), new BigDecimal(dto.getSkuNum()));
        }
        return 1;
    }
}

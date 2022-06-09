package com.bibabo.bibabotrade.services.impl;

import com.bibabo.bibabotrade.services.QueryStockNumServiceI;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;
import com.bibabo.stock.services.StockServiceI;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/6/9
 * @time 14:37
 * @description
 */
@Slf4j
@Service
public class QueryStockNumServiceImpl implements QueryStockNumServiceI {

    @DubboReference(check = false)
    StockServiceI stockService;


    @Override
    public List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList) {
        return stockService.getStock(reqeustDTOList);
    }
}

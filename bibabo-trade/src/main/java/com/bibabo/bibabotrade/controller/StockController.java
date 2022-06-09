package com.bibabo.bibabotrade.controller;

import com.bibabo.bibabotrade.services.QueryStockNumServiceI;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;
import com.bibabo.utils.model.RpcResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/6/9
 * @time 14:39
 * @description
 */
@Slf4j
@RestController
public class StockController {

    @Autowired
    QueryStockNumServiceI queryStockNumService;


    @GetMapping("/stock")
    public RpcResponseDTO<StockResponseDTO> getStockNum(@RequestParam String skuIds) {
        log.info("getStockNum interface receives request paramter : {}", skuIds);

        if (StringUtils.isBlank(skuIds)) {
            return RpcResponseDTO.<StockResponseDTO>builder().fail("请求参数为空").build();
        }

        List<StockReqeustDTO> stockReqeustDTOList = new ArrayList<>();
        Arrays.asList(skuIds.split(",")).forEach(skuId -> {
            StockReqeustDTO dto = new StockReqeustDTO();
            dto.setSkuId(Long.parseLong(skuId));
            stockReqeustDTOList.add(dto);
        });
        List<StockResponseDTO> stockResponseDTOList = queryStockNumService.getStock(stockReqeustDTOList);

        return RpcResponseDTO.<StockResponseDTO>builder().success(stockResponseDTOList).build();
    }
}

package com.bibabo.bibabotrade.controller;

import com.bibabo.utils.model.ResponseDTO;
import com.bibabo.wdm.dto.SkuQueryRequest;
import com.bibabo.wdm.dto.SkuQueryResponse;
import com.bibabo.wdm.services.ISkuDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 19:38
 * @description
 */
@RestController
public class WdmController {


    @DubboReference(check = false, timeout = 300000)
    ISkuDubboService skuDubboService;


    @GetMapping("/sku/{skuId}")
    public ResponseDTO<SkuQueryResponse> getSkuInfoById(@PathVariable long skuId) {
        SkuQueryRequest request = new SkuQueryRequest();
        request.setSkuId(skuId);
        return skuDubboService.findSkuByConditions(request);
    }

    @GetMapping("/sku")
    public ResponseDTO<SkuQueryResponse> getSkuInfosByName(@RequestParam String skuName, @RequestParam Integer from, @RequestParam Integer size) {
        SkuQueryRequest request = new SkuQueryRequest();
        request.setSkuName(skuName);
        request.setFrom(from);
        request.setSize(size);
        return skuDubboService.findSkuByConditions(request);
    }
}

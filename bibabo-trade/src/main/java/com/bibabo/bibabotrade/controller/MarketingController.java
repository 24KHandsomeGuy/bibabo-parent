package com.bibabo.bibabotrade.controller;

import com.bibabo.bibabotrade.services.MarketingServicel;
import com.bibabo.marketing.dto.JoinActivityRequestDTO;
import com.bibabo.utils.model.RpcResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 17:40
 * @Description:
 */
@Slf4j
@RestController
public class MarketingController {

    @Autowired
    private MarketingServicel marketingService;

    @PostMapping("/activity")
    public RpcResponseDTO<Boolean> joinActivity(@RequestBody JoinActivityRequestDTO dto) {
        log.info("join activity receive request parameter:{}", dto);
        return marketingService.joinActivity(dto);
    }

}

package com.bibabo.bibabotrade.services.impl;

import com.bibabo.bibabotrade.services.MarketingServicel;
import com.bibabo.marketing.dto.JoinActivityRequestDTO;
import com.bibabo.marketing.services.MarketingActivityServiceI;
import com.bibabo.utils.model.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 17:41
 * @Description:
 */
@Slf4j
@Service
public class MarketingServiceImpl implements MarketingServicel {

    @DubboReference(check = false, timeout = 30 * 1000)
    private MarketingActivityServiceI marketingActivityService;

    @Override
    public ResponseDTO<Boolean> joinActivity(JoinActivityRequestDTO requestDTO) {
        return marketingActivityService.joinActivity(requestDTO);
    }

}

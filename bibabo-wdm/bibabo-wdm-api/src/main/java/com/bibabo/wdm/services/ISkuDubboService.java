package com.bibabo.wdm.services;

import com.bibabo.utils.model.RpcResponseDTO;
import com.bibabo.wdm.dto.SkuQueryRequest;
import com.bibabo.wdm.dto.SkuQueryResponse;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 10:57
 * @description
 */
public interface ISkuDubboService {

    RpcResponseDTO<SkuQueryResponse> findSkuByConditions(SkuQueryRequest skuQueryRequest);
}

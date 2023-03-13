package com.bibabo.wdm.services;

import com.bibabo.utils.model.RpcResponseDTO;
import com.bibabo.wdm.dto.SkuQueryRequest;
import com.bibabo.wdm.dto.SkuQueryResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 10:57
 * @description
 */
@Path("/")
public interface ISkuDubboService {

    @POST
    @Path("/product")
    RpcResponseDTO<SkuQueryResponse> findSkuByConditions(SkuQueryRequest skuQueryRequest);
}

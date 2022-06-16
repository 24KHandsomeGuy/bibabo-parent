package com.bibabo.bibabowdmservice.services;

import com.bibabo.bibabowdmservice.domain.SkuMain;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 11:22
 * @description
 */
public interface ISkuMainService {

    SkuMain findById(long skuId);

    List<SkuMain> findAll();
}

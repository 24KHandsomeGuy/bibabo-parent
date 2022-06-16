package com.bibabo.bibabowdmservice.services.impl;

import com.bibabo.bibabowdmservice.domain.SkuMain;
import com.bibabo.bibabowdmservice.domain.repository.SkuMainRepository;
import com.bibabo.bibabowdmservice.services.ISkuMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 11:22
 * @description
 */
@Service
public class SkuMainServiceImpl implements ISkuMainService {

    @Autowired
    SkuMainRepository skuMainRepository;

    @Override
    public Optional<SkuMain> findById(long skuId) {
        return skuMainRepository.findById(skuId);
    }

    @Override
    public List<SkuMain> findAll() {
        return skuMainRepository.findAll();
    }
}

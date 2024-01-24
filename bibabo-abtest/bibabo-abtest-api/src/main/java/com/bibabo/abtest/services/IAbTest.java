package com.bibabo.abtest.services;

import com.bibabo.abtest.dto.AbTestRequest;
import com.bibabo.abtest.dto.AbTestResponse;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/13 14:00
 * @Description
 */
public interface IAbTest {

    /**
     * A/B测试获取版本号接口
     *
     * @param abTestRequest A/B测试请求参数
     * @return A/B测试响应结果
     */
    AbTestResponse getAbTest(AbTestRequest abTestRequest);
}

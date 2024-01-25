package com.bibabo.abtest;

import com.alibaba.fastjson.JSONObject;
import com.bibabo.abtest.dto.AbTestRequest;
import com.bibabo.abtest.dto.AbTestResponse;
import com.bibabo.abtest.services.impl.AbTestImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 14:55
 * @Description 测试abtest
 */
@SpringBootApplication
@Slf4j
public class BibaboABTestServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BibaboABTestServiceApplication.class, args);

        AbTestImpl abTest = context.getBean("abTestImpl", AbTestImpl.class);
        // version B
        // AbTestResponse response = abTest.getAbTest(new AbTestRequest.AbTestRequestBuilder().domainId(1L).experimentId(1).userId(10000L).storeId(85L).build());
        // version A
        AbTestResponse response = abTest.getAbTest(new AbTestRequest.AbTestRequestBuilder().domainId(1L).experimentId(1).userId(10010L).storeId(85L).build());
        log.info("AB test service response:{}", JSONObject.toJSONString(response));
    }
}

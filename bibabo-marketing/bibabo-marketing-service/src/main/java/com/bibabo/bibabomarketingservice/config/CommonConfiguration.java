package com.bibabo.bibabomarketingservice.config;

import com.bibabo.bibabomarketingservice.common.SpringContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/4 10:22
 * @Description:
 */
@Configuration
public class CommonConfiguration {

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

}

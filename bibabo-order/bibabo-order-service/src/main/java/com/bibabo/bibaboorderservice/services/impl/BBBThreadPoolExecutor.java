package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.utils.thread.executor.CpuCoresThreadPoolExecutor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fukuixiang
 * @date 2022/4/13
 * @time 13:02
 * @description
 */
@Service
public class BBBThreadPoolExecutor implements InitializingBean, DisposableBean {

    private ThreadPoolExecutor executor;

    @Override
    public void destroy() throws Exception {
        if (executor != null) {
            executor.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new CpuCoresThreadPoolExecutor();
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }
}

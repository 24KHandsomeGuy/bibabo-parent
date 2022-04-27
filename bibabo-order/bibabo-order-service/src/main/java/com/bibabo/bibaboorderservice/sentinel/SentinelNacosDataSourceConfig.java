package com.bibabo.bibaboorderservice.sentinel;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/24
 * @time 15:42
 * @description
 */
/*
 * 规则持久化 - 推模式
 * Sentinel控制台不再是调用客户端的API推送规则数据，而是将规则推送到Nacos或其他远程配置中心
 * Sentinel客户端通过连接Nacos，来获取规则配置；并监听Nacos配置变化，如发生变化，就更新本地缓存（从而让本地缓存总是和Nacos一致）
 * Sentinel控制台也监听Nacos配置变化，如发生变化就更新本地缓存（从而让Sentinel控制台的本地缓存总是和Nacos一致）
 * */
@Configuration
public class SentinelNacosDataSourceConfig {

    @Autowired
    private SentinelProperties sentinelProperties;

    @PostConstruct
    private void init() {
        // NacosSource初始化,从Nacos中获取熔断规则
        sentinelProperties.getDatasource().entrySet().stream().filter(entry -> {
            return entry.getValue().getNacos() != null && entry.getValue().getNacos().getDataId().contains("flow");
        }).forEach(entry -> {
            NacosDataSourceProperties nacos = entry.getValue().getNacos();
            ReadableDataSource<String, List<FlowRule>> flowRuleDataSource
                    = new NacosDataSource<>(nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(), source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            }));
            // nacos控制台更新配置，触发NacosDataSource注册到CacheData中的监听器回调Listener#receiveConfigInfo
            // 通过Conveter转化为FlowRule对象，触发更新flowRuleDataSource.getProperty()=DynamicSentinelProperty中的监听器列表
            // FlowRuleManager会将FlowRuleManager.FlowPropertyListener#configUpdate注册到DynamicSentinelProperty中被回调
            FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        });

        sentinelProperties.getDatasource().entrySet().stream().filter(entry -> {
            return entry.getValue().getNacos() != null && entry.getValue().getNacos().getDataId().contains("degrade");
        }).forEach(entry -> {
            NacosDataSourceProperties nacos = entry.getValue().getNacos();
            ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource
                    = new NacosDataSource<>(nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(), source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
            }));
            DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        });
    }
}

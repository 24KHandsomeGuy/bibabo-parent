# bibabo-parent

该工程使用spring-cloud-alibaba搭建
集成rocketmq、dubbo、seata、sentinel、nacos完成电商系统示例

### 2022-03-20
集成dubbo通信、nacos服务注册中心
1.聚合层trade系统
2.order订单系统微服务

### 2022-04-07
集成rocketmq

### 2022-04-12
集成redis，生成订单号服务

### 2022-04-21
集成spring-cloud-gateway服务网关+sentinel限流

### 2022-04-24
sentinel熔断限流dubbo服务
dubbo+sentinel+nacos配置中心动态刷新sentinel规则配置

### 2022-04-27
新建stock微服务

### 2022-04-27
nacos配置中心集成完毕

### 2022-05-07
支付体系完成

### 2022-05-09
订单系统集成jpa完成

### 2022-05-28
库存系统集成jpa完成

### 2022-05-29
集成Seata，AT模式完成

### 2022-06-07
Order工程集成Canal

### 2022-06-08
Stock工程使用分布式锁保证redis与mysql库存一致

### 2022-06-09
Order工程根据订单Id查询订单增加布隆过滤器校验

### 2022-06-13
使用时间轮，订单未在约定时间内履约需要随机发放超时补偿优惠券给用户

### 2022-06-14
Wdm工程创建完成

### 2022-06-15
Wdm商品从ES中查询，Mysql插入修改数据，Canal同步到ES

### 2022-06-16
所有系统日志上报到ES，index为service-bibabo-log，Kibana日志分析

### 2022-06-17
所有系统集成Zipkin
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

### 2022-06-24
准备新建两个系统
1.营销活动（配置活动规则）
提供接口到前端，用户弹窗参加领卷活动
接口将劵记录落库后，把发劵的动作异步处理，使用双层本地队列处理，接口立即返回
一层队列缓冲"活动决策"是否通过
如果通过，放入本地二层队列
二层队列缓冲"营销资产系统"承载能力

补偿机制：job抓取处理30还未发劵的任务根据状态放入本地queue中
容器关闭补偿机制：spring容器关闭，通过更改信号使得queue的消费者知道容器即将关闭不再消费，把未处理的数据发送到MQ交由其他系统消化
2.用户资产（发放劵到用户账户）用户系统，包含用户资产信息
发放劵到用户账户里
劵的库存放入redis中，先做库存扣减再做劵的存表，尽可能防止超卖，如果存表失败需要加回redis库存，如果异常宕机加回失败则容忍

### 2022-06-26
bibabo营销系统搭建

### 2022-06-26
bibabo营销系统
1.在Spring cloud stream input 和 output之间放置清空队列发送到远程MQ任务
2.消费者完成


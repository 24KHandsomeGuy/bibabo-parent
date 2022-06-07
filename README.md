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
集成Canal

```text
保证Redis与Mysql的数据一致性，本项目演示两种示例

示例1：强一致，只要Mysql修改了，就一定要读到最新的值。库存场景可以这样做，为减少提交订单占库存失败的场景
      stock服务查询库存，先删除Redis缓存，再更新数据库。在删除缓存和更新数据库的临界点存在并发问题，会导致数据不一致，需要加分布式读写锁。
示例2：最终一致，允许极短时间内读到的是未更新的数据，但是一段时间后需要完全一致。比如一些不影响业务结果，只会影响用户体验的操作
      order服务读取订单的收货人信息，Redis中读取，如果修改了收货人信息，先更新数据库，再删除Redis。
      那如果Mysql更新成功，Redis删除失败，可以使事务回滚，但如此的话业务的侵入性就变的很大。
      这里采用Canal伪装成Mysql的Slave节点同步binlog，拿到DML语句做增量同步Redis的最终一致性处理
```
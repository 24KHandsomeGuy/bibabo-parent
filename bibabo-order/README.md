# bibabo-order

## 建表语句

```sql
drop table order_main;
CREATE TABLE `order_main` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单号',
  `order_status` smallint(6) DEFAULT NULL COMMENT '订单状态: 1:新建 2:等待出库   10:取消 11:锁定',
  `is_finish` smallint(6) DEFAULT NULL COMMENT '1:完成 0:结束',
  `ware_id` int(11) DEFAULT NULL COMMENT '库房号',
  `cust_id` bigint(20) DEFAULT NULL COMMENT '用户登录id',
  `cust_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `cust_mobile` varchar(32) DEFAULT NULL COMMENT '用户手机',
  `pay_money` decimal(16,4) DEFAULT NULL COMMENT '应付现金金额',
  `total_price` decimal(16,4) DEFAULT '0.0000' COMMENT '总金额',
  `coupon_price` decimal(16,4) DEFAULT '0.0000' COMMENT '优惠金额',
  `delivery_price` decimal(16,4) DEFAULT '0.0000' COMMENT '运费',
  `is_payed` smallint(6) DEFAULT NULL COMMENT '1:已支付 0:未支付',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '市',
  `county` varchar(64) DEFAULT NULL COMMENT '县区',
  `cust_address` varchar(128) DEFAULT NULL COMMENT '用户地址',
  `order_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `book_date` datetime DEFAULT NULL COMMENT '预约送货日期',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '本系统创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`) USING BTREE,
  KEY `idx_create_date` (`create_date`) USING BTREE,
  KEY `idx_cust_mobile` (`cust_mobile`) USING BTREE,
  KEY `idx_cust_id` (`cust_id`,`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=794215235 DEFAULT CHARSET=utf8 COMMENT='订单主表';

drop table order_detail;
CREATE TABLE `order_detail` (
  `order_detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单明细号',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单号',
  `line_no` int(11) NOT NULL DEFAULT '0',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku id',
  `sku_name` varchar(64) DEFAULT NULL COMMENT 'sku名',
  `ware_id` int(11) DEFAULT NULL COMMENT '库房id',
  `sku_num` int(11) DEFAULT NULL COMMENT 'sku数量',
  `sku_price` decimal(16,4) DEFAULT NULL COMMENT 'sku价格',
  `sale_price` decimal(16,4) DEFAULT NULL COMMENT '销售价',
  `is_gift` smallint(6) DEFAULT NULL COMMENT '是否赠品  1是0否',
  `book_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_detail_id`),
  KEY `idx_order_detail` (`order_id`),
  KEY `idx_create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';
```


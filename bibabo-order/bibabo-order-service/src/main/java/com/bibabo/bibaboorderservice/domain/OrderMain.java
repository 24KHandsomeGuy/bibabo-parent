package com.bibabo.bibaboorderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 12:44
 * @description
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_main")
public class OrderMain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", length = 20, nullable = false)
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @Column(name = "order_status", length = 6, nullable = false)
    @NotNull(message = "订单状态不能为空")
    private Integer orderStatus;

    @Column(name = "is_finish", length = 6)
    private Integer isFinish;

    @Column(name = "ware_id", length = 11, nullable = false)
    @NotNull(message = "订单仓库不能为空")
    private Integer wareId;

    @Column(name = "cust_id", length = 20, nullable = false)
    @NotNull(message = "顾客id不为空")
    private Long custId;

    @Column(name = "cust_name", length = 64)
    private String custName;

    @Column(name = "cust_mobile", length = 32)
    private String custMobile;

    @Column(name = "pay_money", length = 20)
    private BigDecimal payMoney;

    @Column(name = "total_price", length = 20)
    private BigDecimal totalPrice;

    @Column(name = "coupon_price", length = 20)
    private BigDecimal couponPrice;

    @Column(name = "delivery_price", length = 20)
    private BigDecimal deliveryPrice;

    @Column(name = "is_payed", length = 6)
    private Integer isPayed;

    @Column(name = "province", length = 64)
    private String province;

    @Column(name = "city", length = 64)
    private String city;

    @Column(name = "county", length = 64)
    private String county;

    @Column(name = "cust_address", length = 128)
    private String custAddress;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date orderDate;

    @Column(name = "book_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date bookDate;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private List<OrderDetail> orderDetailList;
}

package com.bibabo.bibabouserservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:41
 * @Description:
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_coupon_record")
public class CustomerCouponRecord extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cust_id", length = 20)
    @NotNull(message = "顾客id不能为空")
    private Long custId;

    @Column(name = "coupon_id", length = 20)
    @NotNull(message = "优惠卷id不能为空")
    private Long couponId;

    @Column(name = "used_flag", length = 6)
    private Integer usedFlag;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    @Column(name = "used_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date usedDate;
}

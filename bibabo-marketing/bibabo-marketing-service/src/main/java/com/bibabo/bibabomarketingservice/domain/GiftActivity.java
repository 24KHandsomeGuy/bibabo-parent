package com.bibabo.bibabomarketingservice.domain;

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
import java.util.Date;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:17
 * @description
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_activity")
public class GiftActivity extends BaseEntity {

    @Id
    @Column(name = "activity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Column(name = "name", length = 64, nullable = false)
    @NotNull(message = "活动名称不能为空")
    private String name;

    @Column(name = "sku_ids")
    private String skuIds;

    @Column(name = "coupon_id", length = 20)
    private Long couponId;

    @Column(name = "activity_status", length = 6)
    @NotNull(message = "活动状态不能为空")
    private Integer activityStatus;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id", insertable = false, updatable = false)
    private List<GiftCondition> giftConditions;
}

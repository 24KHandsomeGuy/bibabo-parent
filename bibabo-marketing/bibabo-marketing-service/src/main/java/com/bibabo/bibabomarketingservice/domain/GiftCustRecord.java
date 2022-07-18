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
 * @time 12:44
 * @description
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_cust_record")
public class GiftCustRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_id", length = 20, nullable = false)
    @NotNull(message = "活动id不能为空")
    private Long activityId;

    @Column(name = "cust_id", length = 20, nullable = false)
    @NotNull(message = "顾客id不能为空")
    private Long custId;

    @Column(name = "gift_status", length = 6, nullable = false)
    @NotNull(message = "附赠记录状态不能为空")
    private Integer giftStatus;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}

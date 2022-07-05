package com.bibabo.bibabomarketingservice.domain;

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
@Table(name = "gift_condition")
public class GiftCondition extends BaseEntity {

    @Id
    @Column(name = "condition_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionId;

    @Column(name = "activity_id", length = 20, nullable = false)
    @NotNull(message = "活动id不能为空")
    private Long activityId;

    @Column(name = "code", length = 32)
    private String code;

    @Column(name = "left_bracket", length = 64)
    private String leftBracket;

    @Column(name = "right_bracket", length = 64)
    private String rightBracket;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endDate;

    @Column(name = "lower")
    private String lower;

    @Column(name = "upper", length = 10)
    private String upper;

    @Column(name = "lower_relation_sign", length = 16)
    private String lowerRelationSign;

    @Column(name = "upper_relation_sign", length = 16)
    private String upperRelationSign;

    @Column(name = "operator", length = 16)
    private String operator;

    @Column(name = "del_flag", length = 2)
    private Short delFlag;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}

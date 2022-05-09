package com.bibabo.bibaboorderservice.domain;

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
import java.math.BigDecimal;
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
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", length = 20, nullable = false)
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @Column(name = "line_no", length = 11)
    private Integer lineNo;

    @Column(name = "sku_id", length = 20)
    @NotNull(message = "商品id不能为空")
    private Long skuId;

    @Column(name = "sku_name", length = 64)
    @NotNull(message = "商品名称不能为空")
    private String skuName;

    @Column(name = "ware_id", length = 11, nullable = false)
    @NotNull(message = "商品仓库不能为空")
    private Integer wareId;

    @Column(name = "sku_num", length = 11)
    @NotNull(message = "商品数量不能为空")
    private Integer skuNum;

    @Column(name = "sku_price", length = 20)
    @NotNull(message = "商品标价不能为空")
    private BigDecimal skuPrice;

    @Column(name = "sale_price", length = 20)
    @NotNull(message = "商品售价不能为空")
    private BigDecimal salePrice;

    @Column(name = "is_gift", length = 6)
    private Integer isGift;

    @Column(name = "book_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date bookDate;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}

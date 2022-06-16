package com.bibabo.bibabowdmservice.domain;

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
 * @time 12:44
 * @description
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sku_main")
public class SkuMain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skuId;

    @Column(name = "sku_name", length = 128, nullable = false)
    @NotNull(message = "商品名称不能为空")
    private String skuName;

    @Column(name = "sku_type", length = 6, nullable = false)
    @NotNull(message = "商品类型不能为空")
    private Integer skuType;

    @Column(name = "upc_no", length = 32, nullable = false)
    @NotNull(message = "商品UPC编码不能为空")
    private String upcNo;

    @Column(name = "sale_price", length = 20, nullable = false)
    @NotNull(message = "商品售价不能为空")
    private BigDecimal salePrice;

    @Column(name = "price", length = 20, nullable = false)
    @NotNull(message = "商品标价不能为空")
    private BigDecimal price;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}

package com.bibabo.bibabostockservice.domain;

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
@Table(name = "stock_inventory")
public class StockInventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_id", length = 20, nullable = false)
    private Long skuId;

    @Column(name = "ware_id", length = 11, nullable = false)
    private Integer wareId;

    @Column(name = "stock_num", length = 15)
    private BigDecimal stockNum;

    @Column(name = "order_book_num", length = 15)
    private BigDecimal orderBookNum;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}

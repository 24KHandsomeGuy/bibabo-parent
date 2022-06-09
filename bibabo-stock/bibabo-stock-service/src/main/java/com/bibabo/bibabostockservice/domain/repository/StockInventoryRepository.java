package com.bibabo.bibabostockservice.domain.repository;

import com.bibabo.bibabostockservice.domain.StockInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:59
 * @description 库存
 */
@Repository
public interface StockInventoryRepository extends JpaRepository<StockInventory, Long> {

    StockInventory findBySkuIdAndWareId(long skuId, int wareId);

    @Modifying
    @Query(value = "update stock_inventory set order_book_num = order_book_num + :orderBookNum where id = :id", nativeQuery = true)
    int setOrderBookNumFor(@Param(value = "id") long id, @Param(value = "orderBookNum") BigDecimal orderBookNum);

    /*@Modifying
    @Query(value = "update StockInventory set orderBookNum = ?2 where id = ?1")
    int setOrderBookNumFor(long id, BigDecimal orderBookNum);*/

    StockInventory findBySkuId(long skuId);
}

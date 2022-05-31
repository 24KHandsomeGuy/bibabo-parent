package com.bibabo.bibaboorderservice.domain.repository;

import com.bibabo.bibaboorderservice.domain.OrderMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:59
 * @description
 */
public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {

    OrderMain findByOrderId(long orderId);

    @Modifying
    @Query("update OrderMain set orderStatus = ?2 where orderId = ?1 and orderStatus < ?3")
    int updateOrderStatusCancel(long orderId, int orderStatus, int currentStatus);

    @Modifying
    @Query("update OrderMain set orderStatus = ?2, isPayed = ?4 where orderId = ?1 and orderStatus < ?3")
    int updateOrderIsPayed(long orderId, int status, int status1, int i);
}

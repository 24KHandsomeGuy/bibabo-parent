package com.bibabo.bibaboorderservice.domain.repository;

import com.bibabo.bibaboorderservice.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:59
 * @description
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}

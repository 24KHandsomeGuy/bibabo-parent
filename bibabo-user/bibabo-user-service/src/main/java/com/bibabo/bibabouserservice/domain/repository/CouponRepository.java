package com.bibabo.bibabouserservice.domain.repository;

import com.bibabo.bibabouserservice.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:33
 * @Description:
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}

package com.bibabo.bibabouserservice.domain.repository;

import com.bibabo.bibabouserservice.domain.CustomerCouponRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:34
 * @Description:
 */
@Repository
public interface CustomerCouponRecordRepository extends JpaRepository<CustomerCouponRecord, Long> {

    CustomerCouponRecord findByCouponIdAndCustId(long couponId, long custId);
}

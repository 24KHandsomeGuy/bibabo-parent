package com.bibabo.bibabomarketingservice.domain.repository;

import com.bibabo.bibabomarketingservice.domain.GiftActivity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:59
 * @description
 */
public interface GiftActivityRepository extends JpaRepository<GiftActivity, Long> {

    GiftActivity findByActivityIdAndActivityStatus(long id, int activityStatus);
}

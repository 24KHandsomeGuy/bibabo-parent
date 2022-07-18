package com.bibabo.bibabomarketingservice.domain.repository;

import com.bibabo.bibabomarketingservice.domain.GiftCustRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:59
 * @description
 */
public interface GiftCustRecordRepository extends JpaRepository<GiftCustRecord, Long> {

    @Transactional
    @Modifying
    @Query("update GiftCustRecord set giftStatus = ?2 where id = ?1")
    int updateGiftCustRecordStatus(long id, int giftStatus);

    GiftCustRecord findByActivityIdAndCustIdAndGiftStatus(long activityId, long custId, int giftStatus);
}

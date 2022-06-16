package com.bibabo.bibabowdmservice.domain.repository;

import com.bibabo.bibabowdmservice.domain.SkuMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 15:59
 * @description
 */
@Repository
public interface SkuMainRepository extends JpaRepository<SkuMain, Long> {
}

package com.bibabo.bibabouserservice.services.impl;

import com.bibabo.bibabouserservice.domain.Coupon;
import com.bibabo.bibabouserservice.domain.CustomerCouponRecord;
import com.bibabo.bibabouserservice.domain.repository.CouponRepository;
import com.bibabo.bibabouserservice.domain.repository.CustomerCouponRecordRepository;
import com.bibabo.bibabouserservice.model.enums.RedisPrefixEnum;
import com.bibabo.user.dto.GrantCouponRequestDTO;
import com.bibabo.user.services.GrantCouponService;
import com.bibabo.utils.model.RpcResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;
import java.util.Optional;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:58
 * @Description:
 */
@DubboService
@Slf4j
@RequiredArgsConstructor
public class GrantCouponServiceImpl implements GrantCouponService {

    private final ApplicationContext applicationContext;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CustomerCouponRecordRepository customerCouponRecordRepository;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public RpcResponseDTO grantCoupon(GrantCouponRequestDTO dto) {

        log.info("接收到发劵请求：{}", dto);

        if (dto.getCouponId() == null || dto.getCustId() == null) {
            return RpcResponseDTO.builder().fail("请求参数不能存在空").build();
        }

        Optional<Coupon> couponOptional = couponRepository.findById(dto.getCouponId());
        if (!couponOptional.isPresent()) {
            return RpcResponseDTO.builder().fail("不存在couponId为:" + dto.getCouponId() + "的优惠劵").build();
        }
        if (couponOptional.get().getCouponStatus() != 1) {
            return RpcResponseDTO.builder().fail("优惠劵couponId:" + dto.getCouponId() + "已经失效").build();
        }
        CustomerCouponRecord recordDB = customerCouponRecordRepository.findByCouponIdAndCustId(dto.getCouponId(), dto.getCustId());
        if (recordDB != null) {
            return RpcResponseDTO.builder().fail("优惠劵couponId:" + dto.getCouponId() + ",顾客id:" + dto.getCustId() + "已经发放优惠劵").build();
        }

        CustomerCouponRecord record = CustomerCouponRecord.builder().couponId(dto.getCouponId()).custId(dto.getCustId()).createDate(new Date()).usedFlag(0).build();
        // 先扣减库存、后发劵
        String couponStockKey = RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.COUPON_STOCK, dto.getCouponId());
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(couponStockKey);
        if (rAtomicLong.get() <= 0) {
            rAtomicLong.set(100);
            return RpcResponseDTO.builder().fail("优惠劵couponId:" + dto.getCouponId() + "库存不足发劵失败").build();
        }
        long couponStock = rAtomicLong.decrementAndGet();

        log.info("优惠劵couponId:{}, 顾客id:{} 发放优惠劵扣减库存成功, 剩余库存:{}", dto.getCouponId(), dto.getCustId(), couponStock);
        try {
            customerCouponRecordRepository.save(record);
        } catch (DuplicateKeyException de) {
            log.info("优惠劵couponId:{}, 顾客id:{} 已经发放优惠劵", dto.getCouponId(), dto.getCustId(), de);
            return RpcResponseDTO.builder().fail("优惠劵couponId:" + dto.getCouponId() + ",顾客id:" + dto.getCustId() + "已经发放优惠劵*").build();
        }

        // 采用Spring的Event解耦
        // 发劵成功后同步发送短信通知用户
        applicationContext.publishEvent(new GrantCouponEvent(this, dto.getCustId(), dto.getCouponId()));
        // 异步发送邮箱
        applicationContext.publishEvent(new GrantCouponEventAsync(dto.getCustId(), dto.getCouponId()));

        return RpcResponseDTO.builder().success().build();
    }

}

package com.bibabo.signin.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.bibabo.signin.service.cache.CacheKey;
import com.bibabo.signin.service.model.ao.SignInAO;
import com.bibabo.signin.service.model.vo.RewardInfoVO;
import com.bibabo.signin.service.model.vo.SignInVO;
import com.bibabo.signin.service.repository.domain.RewardInfo;
import com.bibabo.signin.service.service.SignInServiceI;
import com.bibabo.utils.model.ResponseDTO;
import com.bibabo.utils.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RBitSetReactive;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 11:14
 * @Description 签到服务
 * 每1000bit位为一个key，预计1024 / 8 = 128字节 / 1024 = 0.125kb，这样可以确保每个key的bit偏移量足够小
 * 1亿用户，100000000 / 1000 = 100000个key，预计100000 * 0.125kb = 12500kb = 12.5mb
 * 但一般userid会预留，可能出现百亿userid，12.5mb * 100 = 1250mb = 1.25gb
 * 每日1.25gb还是挺大的，而且还要保留连续签到天数
 */
@Slf4j
@Service
public class SignInService implements SignInServiceI {

    private static final int continuousCheckInTimes = 7;

    // key: 连续签到次数，value: 奖励列表
    private final Map<Integer, List<RewardInfo>> rewardInfoMap = new HashMap<>();

    @PostConstruct
    public void init() {
        rewardInfoMap.put(1, Collections.singletonList(new RewardInfo(2, "积分", 1)));
        rewardInfoMap.put(2, Collections.singletonList(new RewardInfo(2, "积分", 1)));
        rewardInfoMap.put(3, Collections.singletonList(new RewardInfo(2, "积分", 2)));
        rewardInfoMap.put(4, Collections.singletonList(new RewardInfo(2, "积分", 4)));
        rewardInfoMap.put(5, Collections.singletonList(new RewardInfo(2, "积分", 6)));
        rewardInfoMap.put(6, Collections.singletonList(new RewardInfo(2, "积分", 8)));
        rewardInfoMap.put(7, Collections.singletonList(new RewardInfo(1, "无门槛10元优惠券", 1)));
    }

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public ResponseDTO<SignInVO> signIn(SignInAO signInAO) throws ExecutionException, InterruptedException {
        if (signInAO == null || signInAO.getUserId() == null || signInAO.getTenantId() == null ||
                signInAO.getPlatform() == null || signInAO.getVendorId() == null) {
            return new ResponseDTO.Builder<SignInVO>().fail("签到参数不能为空").build();
        }
        RedissonReactiveClient reactiveClient = redissonClient.reactive();
        Date now = new Date();
        Instant expireInstant = DateUtils.getEndOfLaterDay(now, continuousCheckInTimes - 1).toInstant();
        // 根据用户&日期做过分片key，每天、每1000个用户为一个key
        String signInKey = CacheKey.getSignInKey(signInAO.getUserId(), signInAO.getTenantId(), now);
        // reactive
        RBitSetReactive rBitSetReactive = reactiveClient.getBitSet(signInKey);
        // 设置过期时间
        Mono<Boolean> signInExpireMono = rBitSetReactive.expire(expireInstant);
        // 计算每1000个用户的偏移量，偏移量位数越多占用bit成指数上升
        long offset = signInAO.getUserId() % 1000;
        // 签到，幂等
        Mono<Boolean> signInResult = rBitSetReactive.set(offset, true);
        if (Boolean.TRUE.equals(signInResult.block())) {
            log.info("用户已签到, userId:{}", signInAO.getUserId());
            return new ResponseDTO.Builder<SignInVO>().fail("用户已签到").build();
        }
        // 签到成功，记录签到排名
        String dailyRankingKey = CacheKey.getSignInDailyRankingKey(signInAO.getTenantId(), now);
        // 因为签到key做了分片，所以不方便统计总签到人数，这里使用redisson的原子递增记录
        RAtomicLongReactive rAtomicLongReactive = reactiveClient.getAtomicLong(dailyRankingKey);
        // 设置过期时间（reactive）
        Mono<Boolean> rankingExpireMono = rAtomicLongReactive.expire(expireInstant);
        // 计算排名（reactive）
        Mono<Long> dailyRankingMono = rAtomicLongReactive.incrementAndGet();
        // 计算连续签到次数，及奖励发放
        // 寻找最近7天的签到记录，当天已签到，寻找前6天
        String[] pastDaysSignInKeys = CacheKey.listSignInKeyPastDays(signInAO.getUserId(), signInAO.getTenantId(), now, continuousCheckInTimes - 1);
        List<Mono<Boolean>> bitSetMonoList = new LinkedList<>();
        for (String pastDaysSignInKey : pastDaysSignInKeys) {
            RBitSetReactive bitsetReactive = reactiveClient.getBitSet(pastDaysSignInKey);
            Mono<Boolean> mono = bitsetReactive.get(offset);
            bitSetMonoList.add(mono);
        }
        // 连续签到次数，今天已签到，初始为1
        AtomicInteger continuousCheckInTimes = new AtomicInteger(1);
        for (Mono<Boolean> mono : bitSetMonoList) {
            Boolean result = mono.block();
            log.info("userId:{}, result:{}", signInAO.getUserId(), result);
            // 未签到，发放奖励
            if (Boolean.FALSE.equals(result)) {
                break;
            }
            // 已签到
            continuousCheckInTimes.getAndIncrement();
        }
        SignInVO signInVO = new SignInVO();
        signInVO.setUserId(signInAO.getUserId());
        signInVO.setContinuousCheckInTimes(continuousCheckInTimes.get());
        Optional<List<RewardInfo>> optional = Optional.ofNullable(rewardInfoMap.get(continuousCheckInTimes.get()));
        optional.ifPresent(rewardInfos -> {
                    List<RewardInfoVO> rewardInfoVOList = rewardInfos.stream()
                            .map(rewardInfo -> new RewardInfoVO(rewardInfo.getType(), rewardInfo.getName(), rewardInfo.getCount()))
                            .collect(Collectors.toList());
                    signInVO.setRewardInfoList(rewardInfoVOList);
                }
        );
        Long dailyRanking = dailyRankingMono.block();
        signInVO.setDailyRanking(dailyRanking);
        log.info("签到成功, sign in result: {}, signInExpireAsync result:{}, rankingExpireAsync result:{}", JSON.toJSONString(signInVO), signInExpireMono.block(), rankingExpireMono.block());
        return new ResponseDTO.Builder<SignInVO>().success(signInVO).build();
    }

}

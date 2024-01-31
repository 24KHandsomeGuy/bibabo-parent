package com.bibabo.signin.service.cache;

import com.bibabo.utils.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 14:29
 * @Description
 */
public class CacheKey {

    private static final String UNDER_LINE = "_";

    @AllArgsConstructor
    @Getter
    public enum Key {
        /**
         * 签到
         */
        SIGN_IN("signIn", "签到"),
        SIGN_IN_RANKING("signInRanking", "签到排名"),
        ;

        private final String key;

        private final String desc;
    }

    public static String getSignInKey(Long userId, Integer tenantId, Date date) {
        // 按照用户ID后三位分片，每个分片1000个用户
        long keySharded = userId / 1000;
        String formatDate = DateUtils.formatDate(date, "yyyy-MM-dd");
        // eg: signIn_1_2024-01-30_12
        return Key.SIGN_IN.getKey() + UNDER_LINE + tenantId + UNDER_LINE + formatDate + UNDER_LINE + keySharded;
    }

    public static String getSignInDailyRankingKey(Integer tenantId, Date date) {
        String formatDate = DateUtils.formatDate(date, "yyyy-MM-dd");
        // eg: signIn_1_2024-01-30
        return Key.SIGN_IN_RANKING.getKey() + UNDER_LINE + tenantId + UNDER_LINE + formatDate;
    }

    public static String[] listSignInKeyPastDays(Long userId, Integer tenantId, Date date, int pastDays) {
        if (pastDays <= 0) {
            return new String[0];
        }
        // 按照用户ID后三位分片，每个分片1000个用户
        long keySharded = userId / 1000;
        String[] keys = new String[pastDays];
        for (int i = 0; i < pastDays; i++) {
            date = org.apache.commons.lang3.time.DateUtils.addDays(date, -1);
            String formatDate = DateUtils.formatDate(date, "yyyy-MM-dd");
            // eg: signIn_1_2024-01-30
            keys[i] = Key.SIGN_IN.getKey() + UNDER_LINE + tenantId + UNDER_LINE + formatDate + UNDER_LINE + keySharded;
        }
        return keys;
    }
}

package com.bibabo.abtest.rule;

import lombok.NonNull;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 16:55
 * @Description
 */
public class GrayPoints {

    private static final String USER_ID_RULE = "userId";

    private static final String STORE_ID_RULE = "storeId";

    private static final String VERSION_RULE = "version";

    private static final String CLIENT_ID_RULE = "clientId";

    public static GrayPoint userId(String value) {
        return new BasicGrayPoint(USER_ID_RULE, value);
    }

    public static GrayPoint storeId(String value) {
        return new BasicGrayPoint(STORE_ID_RULE, value);
    }

    public static GrayPoint version(String value) {
        return new BasicGrayPoint(VERSION_RULE, value);
    }

    public static GrayPoint clientId(String value) {
        return new BasicGrayPoint(CLIENT_ID_RULE, value);
    }

    public static GrayPoint build(@NonNull String name, @NonNull String value) {
        return new BasicGrayPoint(name, value);
    }

    public static GrayPoint suffixGrayPoint(@NonNull String name, @NonNull String value) {
        return new SuffixGrayPoint(name, value);
    }

    private static class SuffixGrayPoint implements GrayPoint {

        private final String name;

        private final String value;

        private SuffixGrayPoint(String name, String sample) {
            this.name = name;
            this.value = sample;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getValue() {
            return value.substring(value.length() - 1);
        }
    }
}

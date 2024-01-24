package com.bibabo.abtest.div;

import java.util.Objects;
import java.util.Random;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:55
 * @Description 分流方式工厂类
 */
public abstract class RuleDivMethods {

    public static IRuleDivMethod global() {
        return IRuleDivMethod.GLOBAL_DIV;
    }

    public static IRuleDivMethod mod(long input) {
        return new Mod100Div(input);
    }

    public static IRuleDivMethod hashMod(Object input) {
        return new HashMod100Div(input);
    }

    public static IRuleDivMethod randomMod(Object input) {
        return new RandomMod100Div(input);
    }

    /**
     * 直接模除100：注意输入是数值型
     */
    private final static class Mod100Div implements IRuleDivMethod {

        private final long input;

        private Mod100Div(Long input) {
            this.input = input;
        }

        @Override
        public int calcIndicator() {
            return (int) Math.abs(input) % 100;
        }
    }

    /**
     * 哈希模除100
     */
    private final static class HashMod100Div implements IRuleDivMethod {

        private final Object input;

        private HashMod100Div(Object input) {
            this.input = input;
        }

        @Override
        public int calcIndicator() {
            int indicator = Objects.hashCode(input);
            return Math.abs(indicator) % 100;
        }
    }

    /**
     * 随机模除100
     */
    private final static class RandomMod100Div implements IRuleDivMethod {

        private final Object input;

        private RandomMod100Div(Object input) {
            this.input = input;
        }

        @Override
        public int calcIndicator() {
            // hash(input) as a random seed
            int indicator = new Random(Objects.hashCode(input)).nextInt();
            return Math.abs(indicator) % 100;
        }
    }

}
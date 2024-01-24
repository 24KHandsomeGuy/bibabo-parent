package com.bibabo.abtest.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:43
 * @Description
 */
public class ABTestUtil {

    /**
     * 对字符串进行MD5哈希散列后编码为字符串返回 ,目的是充分散列原字符串,使AB实验更均匀分布
     *
     * @param str 原始字符串
     * @return 新字符串
     */
    public static String encodeMD5(String str) {
        if (str == null || str.equals("")) {
            throw new RuntimeException("can not encodeMD5 empty string");
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(str.getBytes("utf-8"));
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String encodeStr = base64Encoder.encode(md5Bytes);
            return encodeStr;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 取模（非负）
     *
     * @param str
     * @param baseModulo
     * @return
     */
    public static int hashAndModulo(String str, int baseModulo) {
        if (str == null || str.equals("")) {
            throw new RuntimeException("can not hashAndModulo empty string");
        }
        if (baseModulo <= 0) {
            throw new RuntimeException("can not hashAndModulo when baseModulo<=0");
        }
        int hashCode = str.hashCode();
        if (hashCode < 0) {
            hashCode *= -1;
        }
        int mod = hashCode % baseModulo;
        return mod;
    }
}

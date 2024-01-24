package com.bibabo.abtest.dto;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/13 13:57
 * @Description ab测试请求参数
 */
public class AbTestRequest extends JSONObject implements Serializable {

    /**
     * 实验ID
     */
    protected Integer experimentId;
    /**
     * 域ID
     */
    protected Long domainId;

    /**
     * 用户ID
     */
    protected Long userId;

    /**
     * 版本号
     */
    protected String abFlag;
    /**
     * 设备唯一ID
     */
    protected String uuid;
    /**
     * 设备
     */
    protected String device;
    /**
     * 租户ID
     */
    protected Integer dmTenantId;
    /**
     * 商家ID
     */
    protected Long venderId;
    /**
     * 门店ID
     */
    protected Long storeId;
    /**
     * 平台(IOS、ANDROID)
     */
    protected String platform;

    public AbTestRequest() {
    }

    public AbTestRequest(Long userId, Integer dmTenantId, Long venderId, Long storeId, String platform, Integer experimentId, Long domainId, String abFlag, String uuid, String device) {
        this.userId = userId;
        this.dmTenantId = dmTenantId;
        this.venderId = venderId;
        this.storeId = storeId;
        this.platform = platform;
        this.experimentId = experimentId;
        this.domainId = domainId;
        this.abFlag = abFlag;
        this.uuid = uuid;
        this.device = device;
        //设置属性值到json对象中
        Arrays.stream(ReflectUtil.getFields(AbTestRequest.class))
                // Method和Field将获取到所属类对象
                .filter(field -> AbTestRequest.class.getSimpleName().equals(field.getDeclaringClass().getSimpleName()))
                .forEach(field -> this.put(field.getName(), ReflectUtil.getFieldValue(this, field.getName())));
    }

    public static class AbTestRequestBuilder implements Builder<AbTestRequest> {
        /**
         * 用户ID
         */
        private Long userId;
        /**
         * 版本号
         */
        private String abFlag;
        /**
         * 设备唯一ID
         */
        private String uuid;
        /**
         * 设备
         */
        private String device;
        /**
         * 租户ID
         */
        private Integer dmTenantId;
        /**
         * 商家ID
         */
        private Long venderId;
        /**
         * 门店ID
         */
        private Long storeId;
        /**
         * 平台(IOS、ANDROID)
         */
        private String platform;
        /**
         * 实验ID
         */
        private Integer experimentId;
        /**
         * 域ID
         */
        private Long domainId;
        /**
         * 扩展自定义值
         */
        private final Map<String, Object> values = new HashMap<>();

        public AbTestRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public AbTestRequestBuilder abFlag(String abFlag) {
            this.abFlag = abFlag;
            return this;
        }

        public AbTestRequestBuilder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }


        public AbTestRequestBuilder device(String device) {
            this.device = device;
            return this;
        }

        public AbTestRequestBuilder dmTenantId(Integer dmTenantId) {
            this.dmTenantId = dmTenantId;
            return this;
        }

        public AbTestRequestBuilder venderId(Long venderId) {
            this.venderId = venderId;
            return this;
        }

        public AbTestRequestBuilder storeId(Long storeId) {
            this.storeId = storeId;
            return this;
        }

        public AbTestRequestBuilder platform(String platform) {
            this.platform = platform;
            return this;
        }

        public AbTestRequestBuilder experimentId(Integer experimentId) {
            this.experimentId = experimentId;
            return this;
        }

        public AbTestRequestBuilder domainId(Long domainId) {
            this.domainId = domainId;
            return this;
        }

        public AbTestRequestBuilder put(String key, Object value) {
            this.values.put(key, value);
            return this;
        }

        public AbTestRequestBuilder putAll(Map<String, Object> values) {
            this.values.putAll(values);
            return this;
        }

        @Override
        public AbTestRequest build() {
            AbTestRequest abTestRequest = new AbTestRequest(userId, dmTenantId, venderId, storeId, platform, experimentId, domainId, abFlag, uuid, device);
            abTestRequest.putAll(this.values);
            return abTestRequest;
        }
    }

    public Long getUserId() {
        if (this.userId == null) {
            return this.getLong("userId");
        }
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.put("userId", userId);
    }

    public String getAbFlag() {
        if (this.abFlag == null) {
            return this.getString("abFlag");
        }
        return abFlag;
    }

    public void setAbFlag(String abFlag) {
        this.abFlag = abFlag;
        this.put("abFlag", abFlag);
    }

    public String getUuid() {
        if (this.uuid == null) {
            return this.getString("uuid");
        }
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        this.put("uuid", uuid);
    }

    public String getDevice() {
        if (this.device == null) {
            return this.getString("device");
        }
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
        this.put("device", device);
    }

    public Integer getDmTenantId() {
        if (this.dmTenantId == null) {
            return this.getInteger("dmTenantId");
        }
        return dmTenantId;
    }

    public void setDmTenantId(Integer dmTenantId) {
        this.dmTenantId = dmTenantId;
        this.put("dmTenantId", dmTenantId);
    }

    public Long getVenderId() {
        if (this.venderId == null) {
            return this.getLong("venderId");
        }
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
        this.put("venderId", venderId);
    }

    public Long getStoreId() {
        if (this.storeId == null) {
            return this.getLong("storeId");
        }
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
        this.put("storeId", storeId);
    }

    public String getPlatform() {
        if (this.platform == null) {
            return this.getString("platform");
        }
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
        this.put("platform", platform);
    }

    public Integer getExperimentId() {
        if (this.experimentId == null) {
            return this.getInteger("experimentId");
        }
        return experimentId;
    }

    public void setExperimentId(Integer experimentId) {
        this.experimentId = experimentId;
        this.put("experimentId", experimentId);
    }

    public Long getDomainId() {
        if (this.domainId == null) {
            return this.getLong("domainId");
        }
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
        this.put("domainId", domainId);
    }

}

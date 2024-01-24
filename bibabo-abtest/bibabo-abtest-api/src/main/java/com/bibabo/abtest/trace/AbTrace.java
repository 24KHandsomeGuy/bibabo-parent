package com.bibabo.abtest.trace;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 16:49
 * @Description Trace追踪
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AbTrace extends BaseJsonObj {

    /**
     * 链路跟踪唯一ID
     */
    protected String traceId;
    /**
     * A/B标识
     */
    protected String abTests;

    public AbTrace() {}

    public AbTrace(String traceId) {
        this.traceId = traceId;
    }

    public AbTrace(String traceId, String abTests) {
        this.traceId = traceId;
        this.abTests = abTests;
    }
}

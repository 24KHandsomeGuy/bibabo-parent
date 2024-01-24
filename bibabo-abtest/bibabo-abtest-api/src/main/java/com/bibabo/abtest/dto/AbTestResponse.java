package com.bibabo.abtest.dto;

import com.bibabo.abtest.model.AbVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/13 13:57
 * @Description AB测试返回对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbTestResponse implements Serializable {
    /**
     * ab实验标识(上报用)
     */
    private String abFlag;
    /**
     * ab实验版本
     */
    private AbVersion abVersion;
    /**
     * ab实验数据
     */
    private Object data;

    public boolean isA() {
        if (abVersion == null) {
            return false;
        }
        return AbVersion.A == abVersion;
    }

    public boolean isB() {
        if (abVersion == null) {
            return false;
        }
        return AbVersion.B == abVersion;
    }

    public boolean isC() {
        if (abVersion == null) {
            return false;
        }
        return AbVersion.C == abVersion;
    }

    public boolean isD() {
        if (abVersion == null) {
            return false;
        }
        return AbVersion.D == abVersion;
    }

}

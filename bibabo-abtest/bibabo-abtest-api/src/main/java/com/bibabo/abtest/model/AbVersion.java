package com.bibabo.abtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/13 13:57
 * @Description
 */
@Getter
@AllArgsConstructor
public enum AbVersion {

    A(1, "A"),
    B(2, "B"),
    C(3, "C"),
    D(4, "D"),
    E(5, "E"),
    F(6, "F");

    private final Integer type;

    private final String version;

    public static AbVersion byType(Integer type) {
        for (AbVersion abVersion : AbVersion.values()) {
            if (abVersion.getType().equals(type)) {
                return abVersion;
            }
        }
        return null;
    }

    public static AbVersion byVersion(String version) {
        for (AbVersion abVersion : AbVersion.values()) {
            if (abVersion.getVersion().equals(version)) {
                return abVersion;
            }
        }
        return null;
    }

}

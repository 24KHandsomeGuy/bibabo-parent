package com.bibabo.signin.service.model.ao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 11:20
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInAO implements java.io.Serializable {

    private static final long serialVersionUID = -7813821085175740618L;

    /**
     * 用户ID
     *
     * @required
     * @mock 1
     */
    private Long userId;

    /**
     * 签到的租户ID(数据隔离中台)
     *
     * @required
     * @mock 1
     */
    private Integer tenantId;

    /**
     * 签到平台(数据隔离中台)
     *
     * @required
     * @mock iOS
     */
    private String platform;

    /**
     * 商家ID
     *
     * @required
     * @mock 1
     */
    private Integer vendorId;

}

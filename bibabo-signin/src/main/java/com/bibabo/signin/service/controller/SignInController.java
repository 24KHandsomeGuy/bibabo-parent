package com.bibabo.signin.service.controller;

import com.bibabo.signin.service.model.ao.SignInAO;
import com.bibabo.signin.service.model.vo.SignInVO;
import com.bibabo.signin.service.service.SignInServiceI;
import com.bibabo.utils.model.HttpResult;
import com.bibabo.utils.model.ResponseDTO;
import com.bibabo.utils.model.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 11:10
 * @Description
 */
@RestController
@Slf4j
public class SignInController {

    @Autowired
    private SignInServiceI signInService;

    @PostMapping("/signIn")
    public HttpResult<SignInVO> signIn(@RequestBody SignInAO signInAO) {
        HttpResult<SignInVO> httpResult = new HttpResult<>();
        if (signInAO == null || signInAO.getUserId() == null || signInAO.getTenantId() == null || signInAO.getPlatform() == null || signInAO.getVendorId() == null) {
            return httpResult.failed(ReturnCode.FAILED, "签到参数不能为空");
        }
        ResponseDTO<SignInVO> serviceResponse;
        try {
            serviceResponse = signInService.signIn(signInAO);
        } catch (Exception e) {
            log.error("SignInController.signIn error, signInAO:{}", signInAO, e);
            return httpResult.failed(ReturnCode.FAILED, "签到出现异常: " + e.getMessage());
        }
        httpResult = serviceResponse == null ? httpResult.failed(ReturnCode.FAILED, "签到失败") :
                serviceResponse.getSuccess() ? httpResult.success(serviceResponse.getData()) : httpResult.failed(ReturnCode.FAILED, serviceResponse.getMessage());
        return httpResult;
    }

}

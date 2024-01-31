package com.bibabo.signin.service.service;

import com.bibabo.signin.service.model.ao.SignInAO;
import com.bibabo.signin.service.model.vo.SignInVO;
import com.bibabo.utils.model.ResponseDTO;

import java.util.concurrent.ExecutionException;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 11:15
 * @Description 签到
 */
public interface SignInServiceI {

    /**
     * 签到
     *
     * @param signInAO 签到参数
     * @return 签到结果
     */
    ResponseDTO<SignInVO> signIn(SignInAO signInAO) throws ExecutionException, InterruptedException;
}

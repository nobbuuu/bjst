package com.dream.bjst.net

import com.dream.bjst.bean.LoginBean
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse


/**
 * @author : tiaozi
 * time : 2020/11/9 17:30
 */
object Api {

    /**
     * 获取验证码
     */
    suspend fun getAuthCode(param: String): Map<String, String> {
        //customer/otp
        return RxHttp.postJson("/DB978187809B999186DB9B8084")
            .addAll(param)
            .toResponse<Map<String, String>>()
            .await()
    }

    /**
     * 验证码登录/注册接口
     */
    suspend fun loginOrRegByOtp(param: String): LoginBean {
        //customer/loginOrRegByOtp
        return RxHttp.postJson("/DB978187809B999186DB989B939D9ABB86A69193B68DBB8084")
            .addAll(param)
            .toResponse<LoginBean>()
            .await()
    }


}
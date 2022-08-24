package com.dream.bjst.net

import com.dream.bjst.bean.UserInfo
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse


/**
 * @author : tiaozi
 * time : 2020/11/9 17:30
 */
object Api {

    /**
     * 获取全国各省市区地址
     */
    suspend fun getAdsPictures(): List<UserInfo> {
        return RxHttp.get("/api/areaBase/getAreaBase")
            .add("grade", "3")
            .add("removeAll", "false")
            .toResponse<List<UserInfo>>()
            .await()
    }

    /**
     * 获取验证码
     */
    suspend fun getAuthCode(param: String): Map<String, String> {
        ///customer/otp
        return RxHttp.postJson("/DB978187809B999186DB9B8084")
            .addAll(param)
            .toResponse<Map<String, String>>()
            .await()
    }


}
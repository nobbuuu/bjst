package com.dream.bjst.loan.vm

import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.bean.LoginBean
import com.dream.bjst.bean.LoginParam
import com.dream.bjst.common.UserManager
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import com.tcl.base.kt.ktToastShow
import com.tcl.base.utils.MmkvUtil

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoginViewModel : BaseViewModel() {
    val sendCode = SingleLiveEvent<Boolean>()
    val loginResult = SingleLiveEvent<LoginBean>()
    var mNetToken = ""
    fun sendCode(param: String) {
        rxLaunchUI({
            val result = Api.getAuthCode(param)
            val netToken = result["9A9B8091A09B9F919A"]
            if (!netToken.isNullOrEmpty()) {
                mNetToken = netToken
                sendCode.postValue(true)
            } else {
                "data exception".ktToastShow()
            }
        }, errorBlock = {
            sendCode.postValue(false)
            it.message?.ktToastShow()
        })
    }

    fun login(phone: String, code: String) {
        rxLaunchUI({
            val param = GsonUtils.toJson(
                LoginParam(
                    `978187809B999186B99B969D9891` = phone,
                    `9B8084B79B9091` = code,
                    `9A9B8091A09B9F919A` = mNetToken
                )
            )
            val result = Api.loginOrRegByOtp(param)
            //缓存token
            MmkvUtil.encode(UserManager.getAccessTokenKey(), result.`809B9F919A`)
            loginResult.postValue(result)
        }, errorBlock = {
            it.message?.ktToastShow()
        })
    }
}
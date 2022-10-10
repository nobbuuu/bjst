package com.dream.bjst.login

import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.account.bean.PrivacyBean
import com.dream.bjst.bean.LoginBean
import com.dream.bjst.bean.LoginParam
import com.dream.bjst.common.UserManager
import com.dream.bjst.identification.bean.KYCStatusBean
import com.dream.bjst.net.Api
import com.dream.bjst.common.vm.DeviceInfoViewModel
import com.dream.bjst.utils.DeviceUtils
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import com.tcl.base.kt.ktToastShow
import com.tcl.base.kt.nullToEmpty

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoginViewModel : DeviceInfoViewModel() {
    val sendCode = SingleLiveEvent<Boolean>()
    val loginResult = SingleLiveEvent<LoginBean>()
    val idCardStatus = SingleLiveEvent<KYCStatusBean>()
    val privacyResult = SingleLiveEvent<PrivacyBean>()
    val upDevicePhoto = SingleLiveEvent<Boolean>()
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
                    `9A9B8091A09B9F919A` = mNetToken,
                    `9D84` = DeviceUtils.getGlobalIPAddress()
                )
            )
            val result = Api.loginOrRegByOtp(param)
            //缓存用户数据
            UserManager.setAccessToken(result.`809B9F919A`.nullToEmpty())
            UserManager.setUserNo(result.`9D90`.nullToEmpty())
            UserManager.setCustomerUid(result.`978187809B999186A19D90`.nullToEmpty())
            UserManager.setUserPhone(result.`978187809B999186B99B969D9891`.nullToEmpty())
            UserManager.setUserName(result.`978187809B999186BA959991`.nullToEmpty())
            UserManager.setFalseAccount(result.`939B9B939891A09187809186`)
            loginResult.postValue(result)
        }, errorBlock = {
            it.message?.ktToastShow()
        })
    }

    fun fetchCustomerKycStatus() {
        rxLaunchUI({
            val result = Api.fetchCustomerKycStatus()
            idCardStatus.postValue(result)
        })
    }

    /**
     * 隐私协议
     */
    fun privacy() {
        rxLaunchUI({
            var privacyRes = Api.privacyContent()
            privacyResult.postValue(privacyRes)
        })
    }

}
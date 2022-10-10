package com.dream.bjst.common.vm

import com.dream.bjst.common.UserManager
import com.dream.bjst.identification.bean.KYCStatusBean
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

open class KYCStatusViewModel : BaseViewModel() {

    val userStatus = SingleLiveEvent<KYCStatusBean>()

    fun fetchCustomerKycStatus() {
        if (UserManager.isLogin()) {
            rxLaunchUI({
                val result = Api.fetchCustomerKycStatus()
                userStatus.postValue(result)
            }, showDialog = false)
        }
    }
}
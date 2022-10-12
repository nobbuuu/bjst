package com.kredit.cash.loan.app.common.vm

import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.identification.bean.KYCStatusBean
import com.kredit.cash.loan.app.net.Api
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
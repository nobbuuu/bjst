package com.kredit.cash.loan.app.main.vm

import com.kredit.cash.loan.app.identification.bean.KYCStatusBean
import com.kredit.cash.loan.app.bean.UpgradeDialogBean
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.common.vm.DeviceInfoViewModel
import com.kredit.cash.loan.app.net.Api
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2021/12/8
 *description
 */
class MainViewModel : DeviceInfoViewModel() {
    val upGradeResults = SingleLiveEvent<UpgradeDialogBean>()//版本更新
    val userStatus = SingleLiveEvent<KYCStatusBean>()
    val unLogin = SingleLiveEvent<Boolean>()
    val refreshResult = SingleLiveEvent<Boolean>()

    /**
     * 版本更新
     */
    fun upGradeContent() {
        rxLaunchUI({
            val upGradeResult = Api.upGradeData()
            upGradeResults.postValue(upGradeResult)
        }, showToast = false, showDialog = false)
    }

    fun fetchCustomerKycStatus() {
        if (UserManager.isLogin()) {
            rxLaunchUI({
                val result = Api.fetchCustomerKycStatus()
                userStatus.postValue(result)
            }, showDialog = false, errorBlock = {
                unLogin.postValue(true)
            })
        }
    }


}
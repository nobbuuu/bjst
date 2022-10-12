package com.dream.bjst.main.vm

import com.dream.bjst.identification.bean.KYCStatusBean
import com.dream.bjst.bean.UpgradeDialogBean
import com.dream.bjst.common.Constant
import com.dream.bjst.common.UserManager
import com.dream.bjst.common.vm.DeviceInfoViewModel
import com.dream.bjst.net.Api
import com.dream.bjst.repayment.bean.RepaymentBean
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import com.tcl.base.utils.MmkvUtil

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
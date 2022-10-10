package com.dream.bjst.main.vm

import com.dream.bjst.identification.bean.KYCStatusBean
import com.dream.bjst.bean.UpgradeDialogBean
import com.dream.bjst.common.Constant
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
class MainViewModel : BaseViewModel() {
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
        }, showToast = false)
    }


    fun getIpAddress() {
        rxLaunchUI({
            val result = Api.getIpAddress()
            MmkvUtil.encode(Constant.IPADDRESS,result)
        }, showDialog = false, showToast = false)
    }

    fun fetchCustomerKycStatus() {
        rxLaunchUI({
            val result = Api.fetchCustomerKycStatus()
            userStatus.postValue(result)
        }, showDialog = false, errorBlock = {
            unLogin.postValue(true)
        })
    }


}
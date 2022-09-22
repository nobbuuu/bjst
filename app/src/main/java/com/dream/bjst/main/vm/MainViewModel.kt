package com.dream.bjst.main.vm

import com.dream.bjst.identification.bean.IdCardStatusBean
import com.dream.bjst.login.bean.UpgradeDialogBean
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2021/12/8
 *description
 */
class MainViewModel : BaseViewModel() {
    val upGradeResults = SingleLiveEvent<UpgradeDialogBean>()//版本更新
    val userStatus = SingleLiveEvent<IdCardStatusBean>()

    /**
     * 版本更新
     */
    fun upGradeContent(){
        rxLaunchUI({
            val upGradeResult = Api.upGradeData()
            upGradeResults.postValue(upGradeResult)
        })
    }

    fun fetchCustomerKycStatus() {
        rxLaunchUI({
            val result = Api.fetchCustomerKycStatus()
            userStatus.postValue(result)
        })
    }
}
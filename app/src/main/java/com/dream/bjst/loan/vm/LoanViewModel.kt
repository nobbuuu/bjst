package com.dream.bjst.loan.vm

import com.dream.bjst.bean.UserInfo
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoanViewModel : BaseViewModel() {
    val loginResult = SingleLiveEvent<List<UserInfo>>()
//    fun login(){
//        rxLaunchUI({
//            val  result = Api.getAdsPictures()
//            loginResult.postValue(result)
//        }, errorBlock = {
//
//        })
//    }
}
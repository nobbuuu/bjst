package com.dream.bjst.loan.vm

import com.dream.bjst.loan.bean.LoanInfoBean
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoanViewModel : BaseViewModel() {
    val loanData = SingleLiveEvent<LoanInfoBean>()
    fun fetchProducts(){
        rxLaunchUI({
            val  result = Api.fetchProducts()
            loanData.postValue(result)
        })
    }

    fun startCountDown(){

    }
}
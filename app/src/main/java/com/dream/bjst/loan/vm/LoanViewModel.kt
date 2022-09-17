package com.dream.bjst.loan.vm

import com.dream.bjst.loan.bean.ApplyResultBean
import com.dream.bjst.loan.bean.LoanInfoBean
import com.dream.bjst.loan.bean.LoanPreBean
import com.dream.bjst.loan.bean.OrderResultBean
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
    val loanPreData = SingleLiveEvent<LoanPreBean>()
    val applyData = SingleLiveEvent<ApplyResultBean>()
    val historyData = SingleLiveEvent<OrderResultBean>()
    fun fetchProducts() {
        rxLaunchUI({
            val result = Api.fetchProducts()
            loanData.postValue(result)
        })
    }

    fun fetchCreditAmount() {
        rxLaunchUI({
            val result = Api.fetchCreditAmount()
            loanPreData.postValue(result)
        })
    }

    fun apply(param: String) {
        rxLaunchUI({
            val result = Api.apply(param)
            applyData.postValue(result)
        })
    }
    fun fetchOrderHistory(param: String) {
        rxLaunchUI({
            val result = Api.fetchOrderHistory(param)
            historyData.postValue(result)
        })
    }
}
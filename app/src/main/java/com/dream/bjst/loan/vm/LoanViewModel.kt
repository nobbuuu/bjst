package com.dream.bjst.loan.vm

import com.dream.bjst.identification.bean.KYCStatusBean
import com.dream.bjst.loan.bean.*
import com.dream.bjst.net.Api
import com.dream.bjst.common.vm.DeviceInfoViewModel
import com.dream.bjst.repayment.bean.RepaymentBean
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoanViewModel : DeviceInfoViewModel() {
    val loanData = SingleLiveEvent<LoanInfoBean>()
    val loanPreData = SingleLiveEvent<LoanPreBean>()
    val applyData = SingleLiveEvent<ApplyResultBean>()
    val historyData = SingleLiveEvent<OrderResultBean>()
    val userStatus = SingleLiveEvent<KYCStatusBean>()
    val processOrders = SingleLiveEvent<String>()
    val repaymentResult = SingleLiveEvent<RepaymentBean>()
    val refreshResult = SingleLiveEvent<Boolean>()

    fun fetchCustomerKycStatus() {
        rxLaunchUI({
            val result = Api.fetchCustomerKycStatus()
            userStatus.postValue(result)
        })
    }

    fun fetchProducts() {
        rxLaunchUI({
            val result = Api.fetchProducts()
            loanData.postValue(result)
        }, finalBlock = {
            refreshResult.postValue(true)
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
        }, showDialog = false)
    }

    fun fetchProcessingOrderCount() {
        rxLaunchUI({
            val result = Api.fetchProcessingOrderCount()
            processOrders.postValue(result)
        }, showDialog = false)
    }

    /**
     * 还款数据计划
     */
    fun repaymentData() {
        rxLaunchUI({
            var paymentResult = Api.repayment()
            repaymentResult.postValue(paymentResult)
        }, finalBlock = {
            refreshResult.postValue(true)
        })
    }
}
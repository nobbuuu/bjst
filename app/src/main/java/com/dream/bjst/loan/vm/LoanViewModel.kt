package com.dream.bjst.loan.vm

import com.dream.bjst.identification.bean.IdCardStatusBean
import com.dream.bjst.loan.bean.*
import com.dream.bjst.net.Api
import com.dream.bjst.utils.DeviceUtils
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import java.io.File

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
    val userStatus = SingleLiveEvent<IdCardStatusBean>()
    val processOrders = SingleLiveEvent<String>()
    val localFiles = SingleLiveEvent<List<File>>()

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

    fun getLocalAlbumList() {
        rxLaunchUI({
            val  result = DeviceUtils.getLocalAlbumList(0,50)
            localFiles.postValue(result)
        }, showDialog = false)
    }
}
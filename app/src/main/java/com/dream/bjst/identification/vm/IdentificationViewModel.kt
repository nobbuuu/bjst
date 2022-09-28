package com.dream.bjst.identification.vm

import com.dream.bjst.identification.bean.BankListBean
import com.dream.bjst.identification.bean.ConfirmResultBean
import com.dream.bjst.identification.bean.IdCardDetailsBean
import com.dream.bjst.identification.bean.IdCardStatusBean
import com.dream.bjst.loan.bean.HomeInfoBean
import com.dream.bjst.net.Api
import com.dream.bjst.net.parser.Response
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import com.tcl.base.kt.ktToastShow

/**
 * 创建日期：2022-08-27 on 0:59
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class IdentificationViewModel : BaseViewModel() {

    val idCardInfo = SingleLiveEvent<IdCardDetailsBean>()
    val idCardStatus = SingleLiveEvent<IdCardStatusBean>()
    val idCardDetails = SingleLiveEvent<IdCardDetailsBean>()
    val confirmResult = SingleLiveEvent<ConfirmResultBean>()
    val detectPictureResult = SingleLiveEvent<ConfirmResultBean>()
    val pushContacts = SingleLiveEvent<Boolean>()
    val bankList = SingleLiveEvent<List<BankListBean>>()
    val bindBank = SingleLiveEvent<ConfirmResultBean>()

    val homeData = SingleLiveEvent<HomeInfoBean>()
    fun fetchHomeInfo(){
        rxLaunchUI({
            val  result = Api.fetchHomeInfo()
            homeData.postValue(result)
        })
    }

    fun idCardFrontOcr(param: String) {
        rxLaunchUI({
            val result = Api.idCardFrontOcr(param)
            idCardInfo.postValue(result)
        })
    }

    fun idCardBackOcr(param: String) {
        rxLaunchUI({
            val result = Api.idCardBackOcr(param)
            idCardInfo.postValue(result)
        })
    }

    fun panOcr(param: String) {
        rxLaunchUI({
            val result = Api.panOcr(param)
            idCardInfo.postValue(result)
        })
    }

    fun fetchCustomerKycStatus() {
        rxLaunchUI({
            val result = Api.fetchCustomerKycStatus()
            idCardStatus.postValue(result)
        })
    }

    fun fetchCustomerIdCardInfo() {
        rxLaunchUI({
            val result = Api.fetchCustomerIdCardInfo()
            idCardDetails.postValue(result)
        })
    }

    fun submitAdjustInfo(param: String) {
        rxLaunchUI({
            val result = Api.submitAdjustInfo(param)
            confirmResult.postValue(result)
        })
    }

    fun submitDetectionPicture(param: String) {
        rxLaunchUI({
            var result = Api.submitPictureInfo(param)
            detectPictureResult.postValue(result)
        })
    }

    fun pushUrgencyContact(param: String) {
        rxLaunchUI({
            var result = Api.pushUrgencyContact(param)
            pushContacts.postValue(result)
        })
    }

    fun fetchBanks() {
        rxLaunchUI({
            var result = Api.fetchBanks()
            bankList.postValue(result)
        }, showDialog = false)
    }
    fun customerBindBankCard(param: String) {
        rxLaunchUI({
            var result = Api.customerBindBankCard(param)
            bindBank.postValue(result)
        }, showDialog = false)
    }

}
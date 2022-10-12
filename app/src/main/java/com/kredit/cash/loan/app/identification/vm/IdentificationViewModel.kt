package com.kredit.cash.loan.app.identification.vm

import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.identification.bean.BankListBean
import com.kredit.cash.loan.app.identification.bean.ConfirmResultBean
import com.kredit.cash.loan.app.identification.bean.IdCardDetailsBean
import com.kredit.cash.loan.app.identification.bean.KYCStatusBean
import com.kredit.cash.loan.app.loan.bean.HomeInfoBean
import com.kredit.cash.loan.app.net.Api
import com.kredit.cash.loan.app.common.vm.DeviceInfoViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 * 创建日期：2022-08-27 on 0:59
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class IdentificationViewModel : DeviceInfoViewModel() {

    val idCardInfo = SingleLiveEvent<IdCardDetailsBean>()
    val idCardStatus = SingleLiveEvent<KYCStatusBean>()
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
        if (UserManager.isLogin()){
            rxLaunchUI({
                val result = Api.fetchCustomerKycStatus()
                idCardStatus.postValue(result)
            })
        }
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
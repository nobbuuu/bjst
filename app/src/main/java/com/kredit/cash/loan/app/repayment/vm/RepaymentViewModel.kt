package com.kredit.cash.loan.app.repayment.vm

import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.net.Api
import com.kredit.cash.loan.app.repayment.bean.*
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class RepaymentViewModel : BaseViewModel() {
    val repaymentResult = SingleLiveEvent<RepaymentBean>()
    val repaymentDetailResult = SingleLiveEvent<RepaymentInDetailBean>()
    val repaymentExtendResult = SingleLiveEvent<ExtendRePaymentBean>()
    val repaymentUTRResult = SingleLiveEvent<PaymentUtrBean>()
    val reqRepaymentResult = SingleLiveEvent<requestRepaymentBean>()
    val refreshResult = SingleLiveEvent<Boolean>()

    /**
     * 还款数据计划
     */
    fun repaymentData() {
        if (UserManager.isLogin()) {
            rxLaunchUI({
                var paymentResult = Api.repayment()
                repaymentResult.postValue(paymentResult)
            }, finalBlock = {
                refreshResult.postValue(true)
            })
        } else {
            refreshResult.postValue(true)
        }
    }

    /**
     * 还款详情界面
     */
    fun repaymentDetailData(param: String) {
        rxLaunchUI({
            var payDetailResult = Api.repaymentDetail(param)
            repaymentDetailResult.postValue(payDetailResult)
        })
    }

    /**
     * 延期还款界面
     */
    fun paymentExtendData(param: String) {
        rxLaunchUI({
            var payExtendResult = Api.paymentExtend(param)
            repaymentExtendResult.postValue(payExtendResult)
        })
    }

    /**
     * post UTR数字延期
     */
    fun paymentUTRData(param: String) {
        rxLaunchUI({
            var payUTRResult = Api.payUTRData(param)
            repaymentUTRResult.postValue(payUTRResult)
        })
    }

    /**
     * post 在线还款
     */
    fun reqPaymentData(param: String) {
        rxLaunchUI({
            var reqPResult = Api.reqPayData(param)
            reqRepaymentResult.postValue(reqPResult)
        })
    }

}
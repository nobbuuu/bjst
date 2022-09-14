package com.dream.bjst.repayment.vm

import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.common.UserManager
import com.dream.bjst.net.Api
import com.dream.bjst.repayment.bean.ExtendRePaymentBean
import com.dream.bjst.repayment.bean.PaymentUtrBean
import com.dream.bjst.repayment.bean.RepaymentBean
import com.dream.bjst.repayment.bean.RepaymentInDetailBean
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



    /**
     * 还款数据计划
     */
    fun repaymentData() {
        rxLaunchUI({
            var paymentResult = Api.repayment()
            repaymentResult.postValue(paymentResult)
        })

    }
    /**
     * 还款详情界面
     */
    fun repaymentDetailData(param:String){
        rxLaunchUI({
            var payDetailResult = Api.repaymentDetail(param)
            repaymentDetailResult.postValue(payDetailResult)
        })
    }
    /**
     * 延期还款界面
     */
    fun paymentExtendData(param: String){
        rxLaunchUI({
            var payExtendResult = Api.paymentExtend(param)
            repaymentExtendResult.postValue(payExtendResult)
        })
    }

    /**
     * post UTR数字延期
     */
    fun paymentUTRData(param: String){
        rxLaunchUI({
            var payUTRResult = Api.payUTRData(param)
            repaymentUTRResult.postValue(payUTRResult)
        })
    }
}
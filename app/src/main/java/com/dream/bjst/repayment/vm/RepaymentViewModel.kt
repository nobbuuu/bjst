package com.dream.bjst.repayment.vm

import com.dream.bjst.common.UserManager
import com.dream.bjst.net.Api
import com.dream.bjst.repayment.bean.RepaymentBean
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class RepaymentViewModel : BaseViewModel() {
    val repaymentResult = SingleLiveEvent<RepaymentBean>()

    /**
     * 还款数据计划
     */
    fun repaymentData() {
        rxLaunchUI({
            var paymentResult = Api.repayment()
            //存储用户借款Id
            UserManager.setCustomerLoanId(paymentResult.`9A9B80B08191BB8690918687`.firstOrNull().toString())
            repaymentResult.postValue(paymentResult)
        })

    }
}
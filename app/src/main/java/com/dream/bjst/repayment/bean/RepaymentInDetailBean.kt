package com.dream.bjst.repayment.bean

import java.io.Serializable

data class RepaymentInDetailBean(
    val borrowId: Int,
    val icoUrl: String,
    val orderStatus: Int,
    val principalAmount: Int,
    val productId: Int,
    val productName: String,
    val remainOverdue: Int,
    val remainTotalAmount: Int,
    val repayEnd: String
):Serializable


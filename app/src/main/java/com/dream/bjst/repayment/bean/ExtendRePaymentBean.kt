package com.dream.bjst.repayment.bean

import java.io.Serializable

data class ExtendRePaymentBean(
    val borrowId: Int,
    val principalAmount: Int,
    val repayEnd: String,
    val rollRePayInfoTermList: List<RollRePayInfoTerm>
)

//data class Data(
//
//)

data class RollRePayInfoTerm(
    val delayAmount: Int,
    val delayDays: Int,
    val delayEnd: String,
    val overdueAmount: Int,
    val paidAmount: Int
):Serializable
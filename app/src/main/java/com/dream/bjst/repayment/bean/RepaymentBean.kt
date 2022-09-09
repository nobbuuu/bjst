package com.dream.bjst.repayment.bean

import java.io.Serializable

data class RepaymentBean(
    val dueTodayOrders: List<DueTodayOrder>,
    val notDueOrders: List<NotDueOrder>,
    val overdueOrders: List<OverdueOrder>
)

//data class Data(
//
//)

data class DueTodayOrder(
    val borrowId: Int,
    val dueDay: Int,
    val icoUrl: String,
    val overdueInterestRate: Int,
    val productId: Int,
    val productName: String,
    val remainTotalAmount: Int
)

data class NotDueOrder(
    val borrowId: Int,
    val dueDay: Int,
    val icoUrl: String,
    val overdueInterestRate: Int,
    val productId: Int,
    val productName: String,
    val remainTotalAmount: Int
)

data class OverdueOrder(
    val borrowId: Int,
    val dueDay: Int,
    val icoUrl: String,
    val overdueInterestRate: Int,
    val productId: Int,
    val productName: String,
    val remainTotalAmount: Int
):Serializable
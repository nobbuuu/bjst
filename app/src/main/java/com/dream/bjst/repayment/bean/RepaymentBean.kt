package com.dream.bjst.repayment.bean

import java.io.Serializable

data class RepaymentBean(
    val `908191A09B90958DBB8690918687`: List<DueTodayOrder>?,//当日到期订单
    val `9A9B80B08191BB8690918687`: List<NotDueOrder>?,//未到期订单
    val `9B829186908191BB8690918687`: List<OverdueOrder>?,//逾期中的订单
    val `9B829186908191BD9A809186918780A6958091`: String//逾期时的利率（即计算滞纳金的利率）
):Serializable


data class DueTodayOrder(
    val `969B86869B83BD90`: String,//借款编号
    val `908191B0958D`: Int,//逾期订单为逾期天数,未到期订单为到期天数
    val `9D979BA18698`: String,//图标url
    val `9B829186908191BD9A809186918780A6958091`: String,//逾期时的利率（即计算滞纳金的利率）

    val `84869B90819780BD90`: Int,//产品id
    val `84869B90819780BA959991`: String,//产品名称
    val `869199959D9AA09B809598B5999B819A80`: Int//剩余总待还金额
)

data class NotDueOrder(
    val `969B86869B83BD90`: String,//借款编号
    val `908191B0958D`: Int,//逾期订单为逾期天数,未到期订单为到期天数
    val `9D979BA18698`: String,//图标url
    val `9B829186908191BD9A809186918780A6958091`: String,//逾期时的利率（即计算滞纳金的利率）
    val `84869B90819780BD90`: Int,//产品id
    val `84869B90819780BA959991`: String,//产品名称
    val `869199959D9AA09B809598B5999B819A80`: Int//剩余总待还金额
)

data class OverdueOrder(
    val `969B86869B83BD90`: String,//借款编号
    val `908191B0958D`: Int,//逾期订单为逾期天数,未到期订单为到期天数
    val `9D979BA18698`: String,//图标url
    val `9B829186908191BD9A809186918780A6958091`: String,//逾期时的利率（即计算滞纳金的利率）
    val `84869B90819780BD90`: Int,//产品id
    val `84869B90819780BA959991`: String,//产品名称
    val `869199959D9AA09B809598B5999B819A80`: Int//剩余总待还金额
)
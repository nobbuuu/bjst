package com.dream.bjst.repayment.bean

import java.io.Serializable

data class ExtendRePaymentBean(
    val `969B86869B83BD90`: Int,//订单编号
    val `84869D9A979D849598B5999B819A80`: Int,//借款金额
    val `869184958DB19A90`: String,//到期日期（超过此时间为逾期）
    val `869B9898A691A4958DBD9A929BA0918699B89D8780`: List<RollRePayInfoTerm>//展期费用信息
):Serializable


data class RollRePayInfoTerm(
    val `909198958DB5999B819A80`: Int,//延期还款费用(延期的手续费)
    val `909198958DB0958D87`: Int,//延期天数
    val `909198958DB19A90`: String,//展期/延期到期日期（超过此时间为逾期）
    val `9B829186908191B5999B819A80`: Int,//逾期费用
    val `84959D90B5999B819A80`: Int//应付金额(延期还款费用 + 逾期费用)
)
package com.kredit.cash.loan.app.repayment.bean

import java.io.Serializable

data class requestRepaymentBean(
    val `969B86869B83BD90`: String,//借款编号
    val `969B86869B83A1BD90`: String,//borrowUId
    val `918C849D8691A09D9991`: String,//还款链接有效期
    val `998793`: String,//返回信息
    val `9A919190A69184958DB0958D`: String,//应还日期
    val `84958DB89D9A9F`: String,//还款链接
    val `84958DA08D8491`: String,//支付方式:(0:跳转内部webview浏览器,1:跳转外部浏览器webview)
    val `869199959D9AA09B809598B5999B819A80`: String,//剩余总待还金额
    val `878184849B8680A4958DB59386919199919A80`: List<Any>//支持的还款三方协议
):Serializable


package com.dream.bjst.repayment.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class requestRepaymentParam(
    val `969B86869B83BD90`: String?=null,//还款计划编号
    val `84958DB5999B819A80`: String?=null,//付款金额(非部分还款时可为空)
    val `869184958DA08D8491`: Int?=null//还款类型：10：正常还款，20：部分还款，30：展期还款
):BaseParamBean(),Serializable
package com.dream.bjst.repayment.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class PaymentUtrParam(
//    val `95999B819A80`: String,//payAmount还款金额
    val `969B86869B83BD90`: String,//借款编号
//    val `86919995869F`: String,
    val `818086B79B9091`: String, //UTRCode
    val `818086BD9993A18698B6958791C2C0`: String//Pan图片
):BaseParamBean(),Serializable
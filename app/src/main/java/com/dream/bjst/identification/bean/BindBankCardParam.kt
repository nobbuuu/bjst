package com.dream.bjst.identification.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class BindBankCardParam(
    val `8295989D90958091A08D8491`: String?=null,//10:首次绑定，20：第二次绑定/换卡（需要验卡）
    val `96959A9FA1879186BA959991`: String?=null,//开户人姓名
    val `96959A9FB597979B819A80`: String?=null,//银行账号
    val `96959A9FBA959991`: String?=null,//bankName
    val `9D928797`: String?=null//ifscCode
):Serializable,BaseParamBean()
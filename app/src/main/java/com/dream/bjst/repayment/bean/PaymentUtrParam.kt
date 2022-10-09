package com.dream.bjst.repayment.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class PaymentUtrParam(
    var `818086B79B9091`: String? = null,  //utrCode
    var `818086BD9993A18698B6958791C2C0`: String? = null, //pan图片utr
    var `86919995869F`: String? = null,//描述
    var `95999B819A80`: String? = null, //贷款数量
    var `969B86869B83BD90`: String? = null,//borrowId
    var `979B9984869187879190`: Boolean? = null,//图片是否经过加压
) : BaseParamBean(), Serializable
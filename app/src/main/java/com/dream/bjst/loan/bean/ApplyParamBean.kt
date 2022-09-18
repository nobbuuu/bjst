package com.dream.bjst.loan.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class ApplyParamBean(
    val `84869B9081978087`: List<ApplyListParamBean> = listOf(),
    val `9091829D9791A08D8491`: String? = "",
    val `9D84`: String
) : BaseParamBean(), Serializable

data class ApplyListParamBean(
    val `84869B90819780BD90`: String? = null,
    val `84869D9A979D849598B5999B819A80`: String? = null
) : Serializable
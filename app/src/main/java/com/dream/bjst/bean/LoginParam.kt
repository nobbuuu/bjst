package com.dream.bjst.bean

import java.io.Serializable

data class LoginParam(
    var `978187809B999186B99B969D9891`: String? = null,//手机号
    var `9A9B8091A09B9F919A`: String? = null,//noteToken，otp接口时返回的token
    val `9B8084B79B9091`: String? = null,//验证码
    val `9D84`: String? = null//IP地址
) : Serializable, BaseParamBean()
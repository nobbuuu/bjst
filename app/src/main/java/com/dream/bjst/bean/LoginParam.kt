package com.dream.bjst.bean

import java.io.Serializable

data class LoginParam(
    var `978187809B999186B99B969D9891`: String? = null,//手机号
    var `9A9B8091A09B9F919A`: String? = null,//noteToken，otp接口时返回的token
    val `9B8084B79B9091`: String? = null,//验证码
    val `9D84`: String? = null,//IP地址
    val `869193B3959D90`: String? = null,//注册时的Gaid (谷歌广告id，需要装谷歌play,及翻墙才能获取)

    val `9091829D9791A08D8491`: String? = null,
    val `9091829D9791BD90`: String? = null,
    val `929799A09B9F919A`: String? = null,
    val `958484BD9A8780959A9791BD90`: String? = null,
    val `959A90869B9D909D90`: String? = null,
    val `9895809D80819091`: Int? = null,
    val `989B9A939D80819091`: Int? = null,
) : Serializable, BaseParamBean()
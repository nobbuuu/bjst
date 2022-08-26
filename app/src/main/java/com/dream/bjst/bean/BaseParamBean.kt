package com.dream.bjst.bean

import java.io.Serializable

open class BaseParamBean(
    val `93959D90`: String? = null,//gaid
    val `958484A29186879D9B9A`: String? = null,//包版本号，包版本号，如1,2,3,4数字形式，每更新一个版本+1，通过这个来控制包是否升级
    val `959A90869B9D90BD90`: String? = null,//androidId
    val `978187809B999186BD90`: String? = null,//客户编号,登录之前的接口可为null
    val `9995869F9180BD90`: Int? = 10022,//包编号，每个包约定的编号
) : Serializable
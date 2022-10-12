package com.kredit.cash.loan.app.bean

import com.kredit.cash.loan.app.BuildConfig
import com.kredit.cash.loan.app.common.UserManager
import java.io.Serializable

/**
 * customer基础信息
 */
open class BaseParamBean(
    val `93959D90`: String? = null,//gaid
    val `958484A29186879D9B9A`: String? = BuildConfig.VERSION_CODE.toString(),//appVersion包版本号，包版本号，如1,2,3,4数字形式，每更新一个版本+1，通过这个来控制包是否升级
    val `959A90869B9D90BD90`: String? = null,//androidId
    val `978187809B999186BD90`: String? = UserManager.getUserNo(),  //customerId客户编号,登录之前的接口可为null
    val `9995869F9180BD90`: Int? = 10022,//marketId包编号，每个包约定的编号
//    var `969B86869B83BD90`: String?= UserManager.getCustomerLoanId()//用户借款Id
) : Serializable
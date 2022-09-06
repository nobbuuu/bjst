package com.dream.bjst.account.bean

import java.io.Serializable

/**
 * 创建日期：2022-09-05 on 13:41
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
data class AccountDeleteParam(
    var `958484A29186879D9B9A`: String,//包版本号，包版本号，如1,2,3,4数字形式，每更新一个版本+1，通过这个来控制包是否升级
    var `978187809B999186BD90`: String,//客户编号,登录之前的接口可为null
    var `9995869F9180BD90`: String//包编号，每个包约定的编号
):Serializable
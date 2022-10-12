package com.kredit.cash.loan.app.bean

import java.io.Serializable

data class UpgradeDialogBean(
    val `97818686919A80A29186879D9B9A`: String,//安卓当前版本
    val `869187B58484A18490958091A08C80BD9A929B`: ResAppUpdateTxtInfo?,//更新弹框的内容
    val `86919995869F`: String,
    val `97818686919A80A18698`: String,
    val `99959A9095809B868DA18490958091`: Boolean,
    val `9995869F9180BD90`: Int,
) : Serializable

data class ResAppUpdateTxtInfo(
    val `809D809891`: String,//更新内容标题
    val `808C80BD809199`: List<String>,//更新内容条目
    val `99959A9095809B868DA18490958091`: Boolean?,//是否强制更新，如果是true，则需要强制客户进行升级，即框不能关闭
    val `9995869F9180BD90`: String,//市场（贷超）编号
    val `86919995869F`: String,//备注
    val `97818686919A80A18698`: String//升级链接（谷歌市场url）
) : Serializable
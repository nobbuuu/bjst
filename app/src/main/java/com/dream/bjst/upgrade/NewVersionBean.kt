package com.dream.bjst.upgrade

import java.io.Serializable

/**
 *Author:tiaozi
 *DATE: 2021/7/21
 *DRC: 检测新版本
 */
data class NewVersionBean(
    val createTime: String? = "",
    val force: Boolean? = null,
    val opeTime: String? = "",
    val oper: String? = "",
    val platform: String? = "",
    val popup: String? = "",
    val remarks: String? = "",
    val state: String? = "",
    val storeUuid: String? = "",
    val systemType: String? = "",
    val tenantId: String? = "",
    val url: String? = "",
    val uuid: String? = "",
    var versionNumber: String? = "",
    val content: List<String>,
    //本地是否是最新ApK
    val newVersion: String? = ""
) : Serializable {
    fun isNeedPop() = popup == "0"
    fun isNewVersion() = newVersion == "0"
    fun isForce() = force == null

    /**非强制更新的前提下，无需弹窗 或者 无需更新*/
    fun ableSkipUpgrade() = !isNeedPop() || isNewVersion()

    /**记录版本【以后】*/
    fun getCacheKey() = "versionTip${versionNumber}"
}
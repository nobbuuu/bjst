package com.dream.bjst.net.parser

import java.io.Serializable

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
class Response<T> : Serializable {
    var code: String = ""
    var msg: String? = null
    var data: T? = null


    fun isSuccess(): Boolean {
        return if (data != null && data is SuccessCodeOverrideType) {
            code == SuccessCodeOverrideType::class.java.cast(data)?.getSuccessCode()
        } else {
            code == "200" || code == "0"
        }
    }

    fun isBusinessException(): Boolean = code.startsWith("KY")

    fun isTokenTimeOut(): Boolean = code == "403"

    fun isMultiDeviceLogin(): Boolean = code == "406"

    fun isNotSaleManException(): Boolean = code == "407"

}
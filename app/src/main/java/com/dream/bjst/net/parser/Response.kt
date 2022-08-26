package com.dream.bjst.net.parser

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
class Response<T> : Serializable {

    @SerializedName("979B9091")
    var code: String = ""

    @SerializedName("998793")
    var msg: String? = null//msg

    @SerializedName("998793B79A")
    var msgCn: String? = null//msgCn

    @SerializedName("90958095")
    var data: T? = null//data


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
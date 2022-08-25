package com.dream.bjst.net.parser

import java.io.Serializable

/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
class Response<T> : Serializable {
    var `979B9091`: String = ""//code
    var `998793`: String? = null//msg
    var `998793B79A`: String? = null//msgCn
    var `90958095`: T? = null//data


    fun isSuccess(): Boolean {
        return if (`90958095` != null && `90958095` is SuccessCodeOverrideType) {
            `979B9091` == SuccessCodeOverrideType::class.java.cast(`90958095`)?.getSuccessCode()
        } else {
            `979B9091` == "200" || `979B9091` == "0"
        }
    }

    fun isBusinessException(): Boolean = `979B9091`.startsWith("KY")

    fun isTokenTimeOut(): Boolean = `979B9091` == "403"

    fun isMultiDeviceLogin(): Boolean = `979B9091` == "406"

    fun isNotSaleManException(): Boolean = `979B9091` == "407"

}
package com.tcl.base.kt

import java.lang.Exception
import java.math.BigDecimal

fun Any?.nullToEmpty(defVal: String = ""): String {
    return "${this ?: defVal}"
}

fun String?.deleteUnUseZero(defVal: String = ""): String {
    if (this == null) {
        return defVal
    }
    var result = this
    if (this.indexOf(".") > 0) {
        result = this.replace(Regex("0+?$"), "")
        result = result.replace(Regex("[.]$"), "")
    }
    return result
}

/**
 * 价格计算（乘法运算）（避免丢失精度）
 * this:单价
 * @param num 商品数量
 * @return 单价*数量
 */
fun String?.bigDecimalPrice(num: String = "1"): String {
    try {
        if (this.isNullOrEmpty()) {
            return ""
        }
        return (BigDecimal(this).multiply(BigDecimal(num))).toString().deleteUnUseZero()
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

/**
 * 价格计算（加法运算）（避免丢失精度）
 */
fun String?.bigDecimalPlus(num: String = "0.00"): String {
    try {
        if (this.isNullOrEmpty()) {
            return ""
        }
        return (BigDecimal(this).plus(BigDecimal(num))).toString().deleteUnUseZero()
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

/**
 * 价格计算（减法运算）（避免丢失精度）
 */
fun String?.bigDecimalMinus(num: String = "0.00"): String {
    try {
        if (this.isNullOrEmpty()) {
            return ""
        }
        val minus = BigDecimal(this).minus(BigDecimal(num))
        return minus.toString().deleteUnUseZero()
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}


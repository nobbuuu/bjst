package com.tcl.base.kt

import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import java.io.ByteArrayOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.GZIPOutputStream

/**
 * Author: tiaozi
 * Date : 2021/6/25
 * Drc:
 */
fun String.runLog() {
    LogUtils.iTag("mars", this)
}

/**判断字符串非空并且是非"null"字符串*/
fun String?.isNotEmptyOrNullString(ignoreCase: Boolean = false): Boolean {
    return !isNullOrEmpty() && !"null".equals(this, ignoreCase)
}


fun String.encodePhone(): String {
    return if (length >= 11) {
        "${this.subSequence(0, 3)}****${this.subSequence(7, 11)}"
    } else {
        this
    }
}

/**
 * 字符串去掉换行符，空格等
 */
fun String.remove(format: String = "\\\\s*|\\t|\\r|\\n"): String {
    return Pattern.compile(format).matcher(this).replaceAll("")
}

/**
 * Description: 手机号校验
 * Param:
 * Return: 只校验第一位为1，后面可以是0-9的数字，有10位
 * Author: yk
 * Date: 2021/9/15 17:53
 */
fun isMobileNO(mobiles: String): Boolean {
    val telRegex = "[1]\\d{10}"
    return if (TextUtils.isEmpty(mobiles)) false else mobiles.matches(Regex(telRegex))
}

fun stringFilter(str: String?): String? {
// 只允许字母、数字和汉字
    val regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]" //正则表达式
    val p: Pattern = Pattern.compile(regEx)
    val m: Matcher = p.matcher(str)
    return m.replaceAll("").trim()
}

@JvmOverloads
fun String.GZIPCompress(): String? {
    if (this.isNullOrEmpty()) {
        return this
    }
    val o = ByteArrayOutputStream()
    val g = GZIPOutputStream(o)
    g.write(this.toByteArray(charset("utf-8")))
    g.close()
    return o.toString("ISO-8859-1")
}
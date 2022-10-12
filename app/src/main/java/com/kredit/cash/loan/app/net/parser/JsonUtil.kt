package com.kredit.cash.loan.app.net.parser

import com.blankj.utilcode.util.LogUtils
import org.json.JSONObject
import rxhttp.wrapper.utils.GsonUtil
import java.lang.reflect.Type

object JsonUtil {

    fun <T> getObject(json: String?, type: Type?): T? {
        return try {
            GsonUtil.fromJson(json, type)
        } catch (e: Exception) {
             LogUtils.eTag("RxHttp",e.toString())
            e.printStackTrace()
            null
        }
    }

    /**
     * 需求：合并两个JSONObject
     * (相同的key会被覆盖)
     * @param src1 JSONObject
     * @param src2 JSONObject
     * @return JSONObject
     */
    fun mergeJSONObject(src1: JSONObject, src2: JSONObject): JSONObject {
        return src1.apply {
            src2.keys().forEach { key ->
                val value = src2.get(key)
                put(key, value)
            }
        }
    }
}
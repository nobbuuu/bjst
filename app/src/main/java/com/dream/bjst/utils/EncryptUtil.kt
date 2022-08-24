package com.dream.bjst.utils

import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.bean.UserInfo
import com.google.gson.reflect.TypeToken
import rxhttp.wrapper.utils.GsonUtil
import java.util.*
import kotlin.collections.HashMap
import kotlin.experimental.xor

/**
 * Created by Win on 2022/03/29
 */
object EncryptUtil {

    const val key = "poiuytrggeqwr22fbc"
    const val level = 2

    private fun parseByte2HexStr(buf: ByteArray): String {
        val sb = StringBuffer()
        for (i in buf.indices) {
            var hex = Integer.toHexString((buf[i].toInt() and 0xFF))
            if (hex.length == 1) {
                hex = "0$hex"
            }
            sb.append(hex.uppercase(Locale.getDefault()))
        }
        return sb.toString()
    }

    private fun parseHexStr2Byte(hexStr: String): ByteArray? {
        if (hexStr.isEmpty()) return null
        val result = ByteArray(hexStr.length / 2)
        for (i in 0 until hexStr.length / 2) {
            val high = hexStr.substring(i * 2, i * 2 + 1).toInt(16)
            val low = hexStr.substring(i * 2 + 1, i * 2 + 2).toInt(16)
            result[i] = (high * 16 + low).toByte()
        }
        return result
    }


    fun encode(res: String?): String {
        if (res.isNullOrEmpty()) {
            return ""
        }
        try {
            val bs = res.toByteArray()
            for (i in bs.indices) {
                bs[i] = (bs[i] xor key.hashCode().toByte())
            }
            return parseByte2HexStr(bs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }


    fun decode(res: String?): String {
        if (res.isNullOrEmpty()) {
            return ""
        }
        try {
            val bs = parseHexStr2Byte(res)
            for (i in bs!!.indices) {
                bs[i] = (bs[i] xor key.hashCode().toByte())
            }
            return String(bs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun encryptBody(bodyString: String?): String {
        if (bodyString.isNullOrEmpty()) {
            return ""
        }
        var bodyMap = hashMapOf<String, Any>()
        if (level == 1) {
            //单层即不嵌套
            return bodyString
        } else {
            for (i in 2..level) {
                val currentMap = hashMapOf<String, Any>()
                if (i == 2) {
                    putRanParams(currentMap)
                    currentMap[key] = bodyString
                } else {
                    putRanParams(currentMap)
                    currentMap[key] = bodyMap
                }
                bodyMap = currentMap.clone() as HashMap<String, Any>
            }
            return GsonUtil.toJson(bodyMap)!!
        }
    }

    fun decodeBody(bodyString: String?): String {
        if (bodyString.isNullOrEmpty()) {
            return ""
        }
        var res: Map<String, Any> = GsonUtil.fromJson(bodyString, object : TypeToken<Map<String, Any>>() {}.type)
        var response = ""
        try {
            for (i in 1 until level) {
                val currentRes = decodeRes(res!!)
                if (i != level - 1) {
                    res = currentRes as Map<String, Any>
                } else {
                    response = currentRes as String
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    fun decodeRes(map: Map<String, Any>): Any {
        return map[key]!!
    }

    fun putRanParams(map: HashMap<String, Any>) {
        val randomParams = Random().nextInt(3) + 1
        for (j in 1..randomParams) {
            val key = getRanString(Random().nextInt(10) + 1)
            val value = getRanString(Random().nextInt(10) + 1)
            map[key] = value
        }
    }

    private fun getRanString(length: Int): String {
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0 until length) {
            val number: Int = random.nextInt(str.length - 1)
            sb.append(str[number])
        }
        return sb.toString()
    }
}
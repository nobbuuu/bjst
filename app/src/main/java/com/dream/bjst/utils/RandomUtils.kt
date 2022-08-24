package com.dream.bjst.utils

import org.json.JSONObject

/**
 * 创建日期：2022-08-25 on 0:37
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
object RandomUtils {
    val ramOrigin = listOf(
        "_",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "a",
        "b",
        "c",
        "d",
        "e",
        "f",
        "h",
        "i",
        "j",
        "k",
        "l",
        "m",
        "n",
        "o",
        "p",
        "q",
        "r",
        "s",
        "t",
        "u",
        "v",
        "w",
        "x",
        "y",
        "z"
    )
    val ramSum = listOf(1, 2, 3, 4)
    fun getRomParam(): JSONObject {
        val obj = JSONObject()
        repeat(ramSum.random()) {
            var key = ""
            var value = ""
            repeat(8) {
                key += ramOrigin.random().toString()
                value += ramOrigin.random().toString()
            }
            obj.put(key, value)
        }
        return obj
    }
}
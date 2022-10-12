package com.kredit.cash.loan.app.utils

/**
 * 创建日期：2022-08-25 on 0:37
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
object RandomUtils {
    val ramOrigin = listOf(
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
    fun getRandomParam(): HashMap<String, String> {
        val obj = HashMap<String, String>()
        repeat(ramSum.random()) {
            var key = ""
            var value = ""
            repeat(8) {
                if (it == 4) {
                    key += "_"
                    value += "_"
                } else {
                    key += ramOrigin.random().toString()
                    value += ramOrigin.random().toString()
                }
            }
            obj[key] = value
        }
        return obj
    }
}
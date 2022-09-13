package com.dream.bjst.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    /**
     * 获取距离distance天/月的某一天
     */
    fun getSomeDate(distance: Int = 1, unit: Int? = 10): String? {
        val dft = SimpleDateFormat("yyyy-MM-dd")
        val date = Calendar.getInstance()
        date.time = Date()
        when (unit) {
            10 -> {
                date.set(Calendar.DATE, date.get(Calendar.DATE) + distance)
            }
            20 -> {
                date.add(Calendar.MONTH, distance)
            }
        }
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dft.format(endDate)
    }

    /**
     * 格式化剩余时间
     */
    fun millis2FitTimeSpan(millis: Long): String {
        var tempTime = millis
        if (tempTime < 0) {
            return ""
        }
        val units = arrayOf("天 ", ":", ":", "", "")
        val sb = StringBuilder()
        val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
        for (i in 0 until 4) {
            if (tempTime >= unitLen[i]) {
                val mode = tempTime / unitLen[i]
                tempTime -= mode * unitLen[i]
                if (mode < 10) {
                    sb.append("0$mode").append(units[i])
                } else {
                    sb.append(mode).append(units[i])
                }
            }
        }
        return sb.toString()
    }
}
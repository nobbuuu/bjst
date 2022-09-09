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

}
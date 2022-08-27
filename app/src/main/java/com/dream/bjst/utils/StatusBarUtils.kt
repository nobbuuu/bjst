package com.dream.bjst.utils

import android.app.Activity
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R

object StatusBarUtils {

    /**
     * 沉浸式状态栏
     */
    @JvmStatic
    fun adjustWindow(activity: Activity, color: Int = R.color.transparent, view: View) {
        BarUtils.setStatusBarColor(activity, ColorUtils.getColor(color))
        view.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
    }

    @JvmStatic
    fun adjustWindow(activity: Activity, isLight: Boolean) {
        if (isLight) {
            BarUtils.setStatusBarColor(activity, ColorUtils.getColor(R.color.white))
        } else {
            BarUtils.setStatusBarColor(activity, ColorUtils.getColor(R.color.transparent))
        }
        BarUtils.setStatusBarLightMode(activity, isLight)
    }
}
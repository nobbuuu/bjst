package com.dream.bjst.task

import com.dream.bjst.BuildConfig
import com.dream.bjst.common.MmkvConstant
import com.dream.bjst.net.Configs
import com.tcl.base.utils.MmkvUtil
import com.tcl.launcher.task.MainTask

/**
 *Author:tiaozi
 *DATE: 2021/8/4
 *DRC:
 */
class InitEnvironment : MainTask() {

    override fun run() {
        if (BuildConfig.APP_TYPE != Configs.APP_PRODUCT_TYPE) {
            MmkvUtil.decodeString(MmkvConstant.KEY_DEBUG_CURRENT_TYPE)?.run {
                if (isEmpty()) {
                    Configs.curAppType = BuildConfig.APP_TYPE
                } else {
                    Configs.curAppType =
                        MmkvUtil.decodeString(MmkvConstant.KEY_DEBUG_CURRENT_TYPE)
                            ?: BuildConfig.APP_TYPE
                }
            }
        } else {
            Configs.curAppType = BuildConfig.APP_TYPE
        }
    }

}
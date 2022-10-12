package com.kredit.cash.loan.app.task

import com.blankj.utilcode.util.ActivityUtils
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.login.LoginActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tcl.base.app.BaseConstant
import com.tcl.base.event.LogoutTipsBean
import com.tcl.base.kt.ktStartActivity
import com.tcl.launcher.task.MainTask

/**
 * FileName: ExceptionMonitorTask
 * Author: mars
 * Date: 2021/8/3 11:55 PM
 * Description: 监控1000，406,407
 */
class ExceptionMonitorTask : MainTask() {
    override fun run() {
        LiveEventBus.get(BaseConstant.EVENT_LOGOUT, LogoutTipsBean::class.java).observeForever {
            if (it is LogoutTipsBean) {
                ActivityUtils.getTopActivity()?.run {
                    UserManager.clearUserInfo()
                    ktStartActivity(LoginActivity::class)
                }
            }
        }
    }
}
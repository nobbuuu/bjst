package com.kredit.cash.loan.app.app


import android.app.Activity
import android.app.Application
import android.os.SystemClock
import com.kredit.cash.loan.app.liveness.LivenessResult
import com.kredit.cash.loan.app.task.*
import com.tcl.base.BaseApplication
import com.tcl.base.utils.startAppTime
import com.tcl.launcher.TaskDispatcher
import com.tencent.mmkv.MMKV
/**
 * Author: tiaozi
 * Date : 2021/6/3
 * Drc:
 */
lateinit var mApplication: Application

@Suppress("UNREACHABLE_CODE")
class MyApp : BaseApplication() {
    var browserJsActivityStack: MutableList<Activity>? = null
    var mResult: LivenessResult? = null

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        browserJsActivityStack = mutableListOf()
        TaskDispatcher.init(this)
        //启动器进行异步初始化
        MMKV.initialize(this)

        TaskDispatcher.createInstance()
            .addTask(CatchCrashTask())
            .addTask(InitEnvironment())
            .addTask(InitHttpClient())
            .addTask(InitOtherTask())
            .addTask(ExceptionMonitorTask())
            .start()
        startAppTime = SystemClock.currentThreadTimeMillis()
    }
}


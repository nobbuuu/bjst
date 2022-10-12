package com.kredit.cash.loan.app.app


import android.app.Activity
import android.app.Application
import android.os.SystemClock
import android.util.Log
import com.kredit.cash.loan.app.task.*
import com.liveness.dflivenesslibrary.DFProductResult
import com.liveness.dflivenesslibrary.DFTransferResultInterface
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
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
class MyApp : BaseApplication(), DFTransferResultInterface {
    var browserJsActivityStack: MutableList<Activity>? = null
    var mResult: DFProductResult? = null

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

    override fun setResult(result: DFProductResult?) {
        Log.i(TAG, "setResult: +++++++++++______")
        if (result != null) {
            mResult = result
            Log.i(TAG, "setResult: " + mResult)
        }
    }

    override fun getResult(): DFProductResult? {
        return mResult
    }
}


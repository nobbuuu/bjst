package com.tcl.base

import android.app.Application
import android.content.Context
import com.liveness.dflivenesslibrary.DFProductResult
import com.tcl.base.app.GlobalConfig
import com.tcl.base.app.MVVMTcl
import com.tcl.base.common.ViewModelFactory

/**
 * desc   : BaseApplication
 * author : tanksu
 * date   : 2019-11-17
 */
open class BaseApplication : Application() {
    lateinit var mResult: DFProductResult
    var test = ""
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)



        fun setResult(result: DFProductResult) {
            mResult = result;
        }


        fun getResult(): DFProductResult {
            return mResult
        }
        MVVMTcl.install(GlobalConfig().apply {
            viewModelFactory = ViewModelFactory()
        })

    }
}
package com.kredit.cash.loan.app.other

import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils

object CrashHandler {

   private var isOpen = true

    fun openCatchCrash(isOpen:Boolean = false){
        this.isOpen = isOpen
    }

    fun init(){
        if(isOpen){
            CrashUtils.init {
                LogUtils.eTag("CrashHandler",it.toString())
            }
        }
    }
}
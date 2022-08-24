package com.dream.bjst.loan.vm

import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoginViewModel : BaseViewModel() {
    fun sendCode(param:String){
        rxLaunchUI({
            Api.getAuthCode(param)
        })
    }
    fun login(){
        rxLaunchUI({

        })
    }
}
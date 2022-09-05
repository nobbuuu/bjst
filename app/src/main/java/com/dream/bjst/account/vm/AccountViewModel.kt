package com.dream.bjst.account.vm

import com.dream.bjst.account.bean.AccountDeleteBean
import com.dream.bjst.identification.bean.ConfirmResultBean
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class AccountViewModel : BaseViewModel() {


     val accountDeleteResult=SingleLiveEvent<AccountDeleteBean>()

    /**
     * 删除用户数据
     */
    fun accountDeleteData(param:String){
        rxLaunchUI({
            var accountResult=Api.deleteAccountData(param)
            accountDeleteResult.postValue(accountResult)
        })

    }


}
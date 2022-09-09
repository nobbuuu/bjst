package com.dream.bjst.loan.vm

import com.dream.bjst.loan.bean.HomeInfoBean
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoanViewModel : BaseViewModel() {
    val homeData = SingleLiveEvent<HomeInfoBean>()
    fun fetchHomeInfo(){
        rxLaunchUI({
            val  result = Api.fetchHomeInfo()
            homeData.postValue(result)
        })
    }
}
package com.dream.bjst.home.vm

import com.dream.bjst.loan.bean.HomeInfoBean
import com.dream.bjst.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent

/**
 * 创建日期：2022-08-26 on 23:57
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class HomeViewModel : BaseViewModel() {
    val homeData = SingleLiveEvent<HomeInfoBean>()
    fun fetchHomeInfo(){
        rxLaunchUI({
            val  result = Api.fetchHomeInfo()
            homeData.postValue(result)
        })
    }
}
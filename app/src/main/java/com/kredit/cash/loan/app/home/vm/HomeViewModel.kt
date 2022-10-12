package com.kredit.cash.loan.app.home.vm

import com.kredit.cash.loan.app.common.vm.KYCStatusViewModel
import com.kredit.cash.loan.app.loan.bean.HomeInfoBean
import com.kredit.cash.loan.app.net.Api
import com.tcl.base.event.SingleLiveEvent

/**
 * 创建日期：2022-08-26 on 23:57
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class HomeViewModel : KYCStatusViewModel() {
    val homeData = SingleLiveEvent<HomeInfoBean>()
    fun fetchHomeInfo(){
        rxLaunchUI({
            val  result = Api.fetchHomeInfo()
            homeData.postValue(result)
        })
    }
}
package com.dream.bjst.bean

import com.dream.bjst.common.UserManager

data class DeviceAppsParamBean(
    val `9091829D9791BD9A929BAE9D84A78086`: String? = null,
    val `999B969D9891`: String? = UserManager.getUserPhone()
) : BaseParamBean()


data class AppInfoBean(
    var `8184A09D9991`: Int? = 0,
    var `829186879D9B9AB79B9091`: Int? = 0,
    var `829186879D9B9ABA959991`: String? = null,
    var `8495979FB59391`: String? = null,
    var `9298959387`: Int? = 0,
    var `958484A08D8491`: Int? = 0,
    var `958484BA959991`: String? = null,
    var `9D9AA09D9991`: Int? = 0
)
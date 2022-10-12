package com.kredit.cash.loan.app.bean

data class DeviceUpStatusBean(
    val `9091829D9791A08D8491BD80919987`: List<Int>?,//未上传的设备信息, 10:基础信息 ; 20:app信息 ; 30:短信信息 ; 40:联系人信息 ; 50:通话记录信息 ; 60:相册信息
    val `9C9590A184989B9590`: Boolean//是否上传, true:已上传完成 ; false:未上传完成
)
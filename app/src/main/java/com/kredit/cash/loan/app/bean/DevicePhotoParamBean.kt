package com.kredit.cash.loan.app.bean

import com.kredit.cash.loan.app.common.UserManager

data class DevicePhotoParamBean(
    var `9091829D9791BD9A929BAE9D84A78086`: String? = null,//压缩后的设备信息(相册压缩信息)
    var `999B969D9891`: String? = UserManager.getUserPhone()//mobile
) : BaseParamBean()

data class PhotoInfoBean(
    var `9A959991`: String? = null,//照片名
    var `99959F91`: String? = null,//拍摄者
    var `90958091`: String? = null,//拍摄时间
    var `839D90809C`: String? = null,//照片宽度（单位：像素）
    var `9C919D939C80`: String? = null,//照片高度（单位：像素）
    /*var `9895809D80819091B3`: String? = null,//纬度，小数点方式表达
    var `989B9A939D80819091B3`: String? = null,//经度，小数点方式表达
    var `999B909198`: String? = null,//拍摄机型*/
)
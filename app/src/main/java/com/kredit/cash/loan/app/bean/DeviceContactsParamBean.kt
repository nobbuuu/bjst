package com.kredit.cash.loan.app.bean

import com.kredit.cash.loan.app.common.UserManager

data class DeviceContactsParamBean(
    val `9091829D9791BD9A929BAE9D84A78086`: String?,
    val `999B969D9891`: String? = UserManager.getUserPhone()
):BaseParamBean()

data class ContactsBean(
    var `809D999187B79B9A809597809190`: String? = null,//联系次数
    var `818490958091A09D9991`: String? = null,//更新时间：yyyy-mm-dd HH:mm:ss
    var `849C9B9A91`: String? = null,//电话号码
    var `879B81869791`: String? = null,//手机号对应的来源:'device' 手机号存储在手机上的数据,'sim' 手机号存储在sim卡上的数据
    var `978691958091A09D9991`: String? = null,//创建时间：yyyy-mm-dd HH:mm:ss
    var `979B9A80959780BA959991`: String? = null//联系人名称
)
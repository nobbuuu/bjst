package com.kredit.cash.loan.app.bean

import com.kredit.cash.loan.app.common.UserManager
import java.io.Serializable

data class DeviceLocationParamBean(
    val `989B9795809D9B9A`: LocationBean,
    val `999B969D9891`: String? = UserManager.getUserPhone()
) : BaseParamBean(), Serializable

data class DeviceSMSParamBean(
    val `9091829D9791BD9A929BAE9D84A78086`: String?,
    val `999B969D9891`: String? = UserManager.getUserPhone()
) : BaseParamBean()

data class SMSInfoBean(
    var `808D8491`: Int? = -1,//短信类型 0-发送，1-接收
    var `849C9B9A91`: String? = null,//另一方的手机号
    var `86919590`: Int? = 0,//消息是否被读取
    var `878095808187`: Int? = 0,//TP-消息的状态值，如果未收到状态，则为-1
    var `8781969E919780`: String? = null,//消息的主题，如果存在
    var `8791919A`: Int? = 0,//消息是否被用户看到？ “看到”标志决定我们是否需要显示通知
    var `87919A90BB86909186A69197919D8291A09D9991`: String? = null,//接收或者发送的时间，格式：yyyy-MM-dd HH:mm:ss
    var `95909086918787`: String? = null,//另一方的名字
    var `969B908D`: String? = null//消息的正文
)
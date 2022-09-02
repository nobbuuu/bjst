package com.dream.bjst.identification.bean

import com.dream.bjst.bean.BaseParamBean
import java.io.Serializable

data class DetectionPictureBean(
    val `979B9091`: Int,   //错误代码
    val `90958095`: Data,  //携带的数据
    val `998793`: String,  //错误消息
    val `998793B79A`: String  //错误消息(中文)
)

data class Data(
    val `99918787959391`: String,  //响应信息
    val `869187819880`: Boolean  //是否成功
)
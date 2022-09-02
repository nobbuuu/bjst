package com.dream.bjst.identification.bean

data class DetectionPictureBean(
    val `979B9091`: Int, //Code错误代码
    val `90958095`: Data,//携带数据
    val `998793`: String,  //错误信息
    val `998793B79A`: String  //中文错误信息
)

data class Data(
    val `989D8291BA918787B79B9984959A8D`: Int  //当前使用的活体认证公司，10：advance，20：flashRisk,30:accAuth
)
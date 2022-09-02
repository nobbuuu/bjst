package com.dream.bjst.identification.bean

import android.graphics.Bitmap
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo
import com.dream.bjst.bean.BaseParamBean
import java.io.File
import java.io.Serializable

data class DetectionPictureParam(
    //val `958484A29186879D9B9A`: String, //包版本号，包版本号，如1,2,3,4数字形式，每更新一个版本+1，通过这个来控制包是否升级
    //val `978187809B999186BD90`: Int,  //客户编号,登录之前的接口可为null
    val `92959791BD9993B6958791C2C0`: String,  //人脸图的base64
    val `989D8291BA918787B29D9891B6958791C2C0`: CharArray?, //文件base64
    //val `9995869F9180BD90`: Int  //包编号，每个包约定的编号
):Serializable,BaseParamBean()
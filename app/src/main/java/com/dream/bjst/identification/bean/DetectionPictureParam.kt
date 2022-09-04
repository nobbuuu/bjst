package com.dream.bjst.identification.bean

import android.graphics.Bitmap
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo
import com.dream.bjst.bean.BaseParamBean
import java.io.File
import java.io.Serializable

data class DetectionPictureParam(
    val `92959791BD9993B6958791C2C0`: String,  //人脸图的base64
    val `989D8291BA918787B29D9891B6958791C2C0`: String, //文件base64
):Serializable,BaseParamBean()
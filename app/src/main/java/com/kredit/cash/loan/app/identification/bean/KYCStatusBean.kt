package com.kredit.cash.loan.app.identification.bean

import java.io.Serializable

data class KYCStatusBean(
    val `84959AA49C9B809BA18698`: String? = null,
    val `959898BD809199A4958787`: Boolean,//是否所有项目都通过
    val `969895979F`: Boolean,
    val `9A919190B09B9D9A93BD809199`: String?,//当前进行到的项目类型,null表示都通过，10：身份证前ocr（跳转到证件ocr界面）,11:身份证后ocr（跳转到证件ocr界面）,20:身份证前ocr（跳转到证件ocr界面）,30:pan卡ocr（跳转到证件ocr界面）,31:pan号认证（跳转到证件ocr界面）,40:活体检测,50:人脸对比,51:人脸对比（跳转到活体）,60:紧急联系人认证,70:扩展信息录入,80:银行卡绑定，90：银行卡重绑定
    val `9D90B7958690B2869B9A80A49C9B809BA18698`: String? = null,
    val `9D90B7958690B695979FA49C9B809BA18698`: String? = null
) : Serializable
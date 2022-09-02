package com.dream.bjst.identification.bean

import java.io.Serializable

data class IdCardInfoBean(
    val `93919A909186`: Int,//性别
    val `969D86809C90958D`: String,//生日
    val `9A959991`: String,//姓名
    val `9D90BA8199969186`: String,//身份证号
    val `9295809C9186BA959991`: String,
):Serializable

data class IdCardStatusBean(
    val `84959AA49C9B809BA18698`: String? = null,
    val `959898BD809199A4958787`: Boolean,
    val `969895979F`: Boolean,
    val `9A919190B09B9D9A93BD809199`: String?,//当前进行到的项目类型,null表示都通过，10：身份证前ocr（跳转到证件ocr界面）,11:身份证后ocr（跳转到证件ocr界面）,20:身份证前ocr（跳转到证件ocr界面）,30:pan卡ocr（跳转到证件ocr界面）,31:pan号认证（跳转到证件ocr界面）,40:活体检测,50:人脸对比,51:人脸对比（跳转到活体）,60:紧急联系人认证,70:扩展信息录入,80:银行卡绑定，90：银行卡重绑定
    val `9D90B7958690B2869B9A80A49C9B809BA18698`: String? = null,
    val `9D90B7958690B695979FA49C9B809BA18698`: String? = null
):Serializable
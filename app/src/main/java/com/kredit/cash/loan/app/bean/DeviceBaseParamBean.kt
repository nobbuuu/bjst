package com.kredit.cash.loan.app.bean

import com.kredit.cash.loan.app.common.UserManager
import java.io.Serializable

data class DeviceBaseParamBean(
    val `9091829D9791BD9A929BAE9D84A78086`: String?,
    val `999B969D9891`: String? = UserManager.getUserPhone()
) : BaseParamBean(), Serializable

data class A80AB839D929D(
    val `87879D90`: String,
    val `999597`: String
)

data class WifiConnectBean(
    val `839D929DAB979B819A80`: Int,
    val `97818686919A80AB839D929D`: A80AB839D929D? = null,
    val `979B9A929D9381869190AB839D929D`: List<B9A929D9381869190AB839D929D>? = null,
    val `9D84`: String? = null
)

data class GeneralData(
    val `809D9991AE9B9A91BD90`: String? = null,
    val `81819D90`: String? = null,
    val `818796B091968193`: Boolean? = false,
    val `81879D9A93A2849A`: Boolean? = false,
    val `81879D9A93A4869B8C8DA49B8680`: Boolean = false,
    val `849C9B9A91A08D8491`: String? = null,
    val `849C9B9A91BA8199969186`: String? = null,
    val `869B9B80`: Boolean? = false,
    val `879D99819895809B86`: Boolean? = false,
    val `9091829D9791BD90`: String? = null,
    val `909699`: Int? = 0,
    val `909A87`: String? = null,
    val `91989584879190A6919598809D9991`: Long? = null,
    val `9395BD90`: String? = null,
    val `959A90BD90`: String? = null,
    val `979D90`: String? = null,
    val `98958780A69B9B80A09D9991`: Int? = null,
    val `98959A9381959391`: String? = null,
    val `989B97959891B09D878498958DB8959A9381959391`: String? = null,
    val `989B97959891BD879BC7B79B819A80868D`: String? = null,
    val `989B97959891BD879BC7B8959A9381959391`: String? = null,
    val `999597`: String? = null,
    val `999797`: String? = null,
    val `999A97`: String? = null,
    val `9A9180839B869FA08D8491`: String? = null,
    val `9A9180839B869FBB84918695809B86`: String? = null,
    val `9A9180839B869FBB84918695809B86BA959991`: String? = null,
    val `9D99879D`: String? = null,
    val `9D99919D`: String? = null
)

data class B9A929D9381869190AB839D929D(
    val `87879D90`: String,
    val `9687879D90`: String,
    val `9A959991`: String
)

data class B9B809C(
    val `8780958091`: Int,
    val `999597B5909086918787`: String,
    val `9A959991`: String
)

data class LocationBean(
    val `938487`: GpsBean,
    var `938487B5909086918787A4869B829D9A9791`: String,//省
    val `938487B5909086918787A78086919180`: String? = null,
    val `938487B5909086918787A799959898B09D8780869D9780`: String? = null,
    var `938487B5909086918787B79D808D`: String,//市
    var `938487B5909086918787B895869391B09D8780869D9780`: String//区
)

data class StorageBean(
    val `869599A09B809598A79D8E91`: Long = 0,
    val `869599A18795969891A79D8E91`: Long = 0,
    val `918C8091869A9598A7809B86959391`: String? = null,
    val `9991999B868DB7958690A79D8E91`: Long = 0,
    val `9991999B868DB7958690A79D8E91A18791`: Long = 0,
    val `99959D9AA7809B86959391`: String? = null
)

data class BatteryStatusBean(
    val `9695808091868DA49780`: String? = null,
    val `9D87A18796B79C95869391`: Int = 0,
    val `9D87B597B79C95869391`: Int = 0,
    val `9D87B79C9586939D9A93`: Int = 0
)

data class DeviceBaseInfoBean(
    val `87809B86959391`: StorageBean,
    val `9091829D9791B29D9891B79A80`: DeviceFileCnt,
    val `9091829D9791B695808091868DBD9A929B`: String? = null,
    val `9091829D9791B991999B868DBD9A929B`: String? = null,
    val `93919A91869598B0958095`: GeneralData,
    val `9695808091868DA78095808187`: BatteryStatusBean,
    val `96988191809B9B809C`: B9B809C? = null,
    val `989B9795809D9B9A`: LocationBean,
    val `9A9180839B869F`: WifiConnectBean,
    val `9C95869083958691`: HardwareBean
)


data class DeviceFileCnt(
    val `829D90919BB18C8091869A9598`: Int,
    val `829D90919BBD9A8091869A9598`: Int,
    val `909B839A989B9590B29D989187`: Int,
    val `9581909D9BB18C8091869A9598`: Int,
    val `9581909D9BBD9A8091869A9598`: Int,
    val `9D9995939187B18C8091869A9598`: Int,
    val `9D9995939187BD9A8091869A9598`: Int
)

data class HardwareBean(
    val `808D8491`: String? = null,
    val `80959387`: String? = null,
    val `809D9991`: String? = null,
    val `81879186`: String? = null,
    val `84869B90819780`: String? = null,
    val `849C8D879D979598A79D8E91`: String? = null,
    val `86919891958791`: String? = null,
    val `8695909D9BA29186879D9B9A`: String? = null,
    val `878D87809199A29186879D9B9A`: String? = null,
    val `87909FA29186879D9B9A`: String? = null,
    val `8791869D9598`: String? = null,
    val `8791869D9598BA8199969186`: String? = null,
    val `9091829D9791`: String? = null,
    val `9091829D9791BA959991`: String? = null,
    val `909D878498958D`: String? = null,
    val `929D9A93918684869D9A80`: String? = null,
    val `9686959A90`: String? = null,
    val `969B958690`: String? = null,
    val `969B9B80989B95909186`: String? = null,
    val `978481A08D8491`: String? = null,
    val `99959A8192959780818691869A959991`: String? = null,
    val `999B909198`: String? = null,
    val `999B909198BA959991`: String? = null,
    val `9C95869083958691`: String? = null,
    val `9C959A90879180B9959F918687`: String? = null,
    val `9C9B8780`: String? = null,
    val `9D90`: String? = null
)

data class GpsBean(
    val `9895809D80819091`: Double? = null,
    val `989B9A939D80819091`: Double? = null
)
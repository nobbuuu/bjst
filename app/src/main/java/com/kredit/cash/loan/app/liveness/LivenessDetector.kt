package com.kredit.cash.loan.app.liveness

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.hardware.Camera
import android.hardware.Sensor
import android.os.Build
import android.widget.Toast
import com.dfsdk.liveness.DFLivenessSDK
import com.dfsdk.liveness.DFLivenessSDK.*
import java.io.File
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by Win on 2022/10/21
 */
class LivenessDetector(context: Activity) : Camera.PreviewCallback {
    private var mListener: OnLivenessCallBack? = null
    private var isStop = false
    private var isPaused = true
    private var nv21DataIsReady = false
    private var nv21Datas: ByteArray? = null
    private var motions = arrayOf(DFLivenessMotion.HOLD_STILL)
    private var results = arrayOf(false)
    private var position = 0
    private var livenessSDK: DFLivenessSDK? = null
    private var isFirstPreview = true
    private var firstPreviewTime = 0L
    private var isFistShowWaiting = true
    private var isEndShowWaiting = false
    private var isSuccess = false
    private var sdkHandlerIsSuccess = false
    private var service: ExecutorService? = null
    private var mContext: WeakReference<Context>
    private var runnable: LivenessRunnable? = null

    init {
        mContext = WeakReference(context)
    }

    class LivenessRunnable(process: LivenessDetector) : Runnable {
        private val mWeakRef: WeakReference<LivenessDetector>
        override fun run() {
            val process = mWeakRef.get()
            process?.doRunnable()
        }

        init {
            mWeakRef = WeakReference(process)
        }
    }

    private fun doRunnable() {
        while (!isStop) {
            try {
                Thread.sleep(15)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            if (isPaused) {
                continue
            }
            if (!isPaused && isEndShowWaiting) {
                synchronized(this) { initSDK() }
                doDetect()
                nv21DataIsReady = false
            }
        }
        releaseDetector()
    }


    private fun releaseDetector() {
        synchronized(this) {
            livenessSDK?.end()
            livenessSDK?.destroy()
            livenessSDK = null
            runnable = null
        }
    }

    fun doDetect() {
        var status: DFStatus? = null
        if (livenessSDK != null) {
            try {
                if (position < motions.size) {
                    if (isSuccess) {
                        synchronized(nv21Datas!!) {
                            status = livenessSDK!!.detect(
                                nv21Datas,
                                PIX_FMT_NV21,
                                CameraPhysics.previewWidth,
                                CameraPhysics.previewHeight,
                                mListener!!.getOrientation(),
                                motions[position]
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (status != null && status!!.detectStatus != DFDetectStatus.FRAME_SKIP.value) {
            val faceRect = status!!.faceRect
            mListener?.onDectCallback(
                motions[position].value,
                status!!.isHasFace,
                status!!.isFaceValid,
                faceRect
            )
            if (status!!.detectStatus == DFDetectStatus.PASSED.value && status!!.isPassed) {
                if (position < motions.size) {
                    results[position] = true
                    if (results[position]) {
                        position++
                        livenessSDK!!.detect(
                            nv21Datas,
                            PIX_FMT_NV21,
                            CameraPhysics.previewWidth,
                            CameraPhysics.previewHeight,
                            mListener!!.getOrientation(),
                            DFLivenessMotion.NONE
                        )
                        if (position == motions.size) {
                            onFinishDetect(LivenessStatus.Success)
                        }
                    }
                }
            }
        }
        mListener?.addBuffer()
    }

    private fun onFinishDetect(status: LivenessStatus) {
        stopLiveness()
        mListener?.onStatusCallback(
            status, getLivenessResult(), getImageResult()
        )
        releaseDetector()
    }

    private fun getLivenessResult(): ByteArray? {
        try {
            synchronized(this) {
                if (livenessSDK != null) {
                    livenessSDK!!.end()
                    return livenessSDK!!.livenessResult
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getImageResult(): Array<DFLivenessImageResult>? {
        try {
            synchronized(this) {
                if (livenessSDK != null) {
                    livenessSDK!!.end()
                    return livenessSDK!!.imageResult
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    fun setSDKStaticInfo() {
        livenessSDK?.apply {
            try {
                setStaticInfo(DFWrapperStaticInfo.DEVICE.value, Build.MODEL)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                setStaticInfo(DFWrapperStaticInfo.OS.value, "Android")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                setStaticInfo(
                    DFWrapperStaticInfo.SYS_VERSION.value,
                    Build.VERSION.RELEASE
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                setStaticInfo(DFWrapperStaticInfo.ROOT.value, isRootSystem().toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                setStaticInfo(
                    DFWrapperStaticInfo.CUSTOMER.value,
                    mContext.get()?.applicationContext?.packageName
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun initSDK() {
        try {
            if (livenessSDK == null) {
                mContext.get()?.let {
                    livenessSDK = DFLivenessSDK(it)
                    val createResultCode = livenessSDK!!.createHandle()
                    sdkHandlerIsSuccess = createResultCode == DF_LIVENESS_INIT_SUCCESS
                    if (sdkHandlerIsSuccess) {
                        isSuccess =
                            livenessSDK!!.start(
                                DFLivenessOutputType.getOutputTypeByValue("multiImg").value,
                                motions
                            )
                        setDetectorParameters(livenessSDK!!)
                        if (isSuccess) {
                            setSDKStaticInfo()
                        }
                    } else {
                        createHandleError(createResultCode)
                    }
                }
            }
        } catch (e: Throwable) {
            createHandleError(1001)
        }
    }

    private fun createHandleError(resultCode: Int) {
        val errorInfo = when (resultCode) {
            DF_LIVENESS_INIT_FAIL_OUT_OF_DATE -> {
                "Device time is not within the validity period of the license, pls use the valid license"
            }
            DF_LIVENESS_INIT_FAIL_BIND_APPLICATION_ID -> {
                "The package name in the license does not match with application ID, please use the right license file"
            }
            else -> {
                "Liveness detection failed, please try again"
            }
        }
        val context = mContext.get()
        Toast.makeText(context, errorInfo, Toast.LENGTH_SHORT).show()
        mListener?.onError(resultCode)
    }

    fun setDetectorParameters(detector: DFLivenessSDK) {
        detector.setThreshold(DFLivenessKey.KEY_SILENT_DETECT_NUMBER, 1f)
        detector.setThreshold(DFLivenessKey.KEY_SILENT_FACE_RET_MAX_RATE, 0.60f)
        detector.setThreshold(DFLivenessKey.KEY_SILENT_TIME_INTERVAL, 30f)
        detector.setThreshold(DFLivenessKey.KEY_SILENT_FACE_OFFSET_RATE, 0.4f)
        detector.setThreshold(DFLivenessKey.KEY_BLUR_THRESHOLD, 0.3f)
        try {
            mListener?.let {
                detector.setSilentDetectRegion(it.getDetectRegion())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun registerLivenessDetectCallback(callback: OnLivenessCallBack?) {
        mListener = callback
    }

    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        if (service == null) {
            position = 0
            mListener?.let {
                nv21Datas = null
                nv21Datas = ByteArray(
                    CameraPhysics.previewWidth * CameraPhysics.previewHeight * 3
                            / 2
                )
            }
            service = Executors.newSingleThreadExecutor()
            runnable = LivenessRunnable(this)
            service?.execute(runnable)
        }
        if (isFirstPreview) {
            firstPreviewTime = System.currentTimeMillis()
            isFirstPreview = false
        }
        val intervalTime = System.currentTimeMillis() - firstPreviewTime
        if (intervalTime <= 1000) {
            if (isFistShowWaiting) {
                mListener?.onStatusCallback(
                    LivenessStatus.InitWaiting, null, null
                )
                isFistShowWaiting = false
            }
            mListener?.addBuffer()
        } else {
            if (!isEndShowWaiting) {
                mListener?.onStatusCallback(LivenessStatus.EndWaiting, null, null)
                isEndShowWaiting = true
                startLiveness()
            }
            if (!isPaused && !nv21DataIsReady) {
                nv21Datas?.let {
                    synchronized(it) {
                        if (data != null && it.size >= data.size) {
                            System.arraycopy(data, 0, it, 0, data.size)
                            nv21DataIsReady = true
                        }
                    }
                }
            }
        }
    }

    class CameraInfo {
        var width = 0
        var height = 0
        var orientation = 0
    }

    interface OnLivenessCallBack {
        fun onStatusCallback(
            livenessStatus: LivenessStatus, livenessEncryptResult: ByteArray?,
            imageResult: Array<DFLivenessImageResult>?
        )

        fun onDectCallback(value: Int, hasFace: Boolean, faceValid: Boolean, rect: DFRect)
        fun getDetectRegion(): RectF?
        fun getOrientation(): Int
        fun addBuffer()
        fun onError(error: Int)
    }

    fun stopLiveness() {
        isPaused = true
    }

    fun startLiveness() {
        results[0] = false
        position = 0
        isPaused = false
    }

    fun addSequentialInfo(type: Int, values: FloatArray) {
        if (!isPaused && livenessSDK != null && sdkHandlerIsSuccess) {
            val sb = StringBuilder()
            sb.append(values[0])
                .append(" ")
                .append(values[1])
                .append(" ")
                .append(values[2])
                .append(" ")
            var sequentialInfo: DFWrapperSequentialInfo? = null
            when (type) {
                Sensor.TYPE_MAGNETIC_FIELD -> sequentialInfo =
                    DFWrapperSequentialInfo.MAGNETIC_FIELD
                Sensor.TYPE_ACCELEROMETER -> sequentialInfo = DFWrapperSequentialInfo.ACCLERATION
                Sensor.TYPE_ROTATION_VECTOR -> sequentialInfo =
                    DFWrapperSequentialInfo.ROTATION_RATE
                Sensor.TYPE_GRAVITY -> sequentialInfo = DFWrapperSequentialInfo.GRAVITY
                else -> {}
            }
            try {
                if (sequentialInfo != null) {
                    livenessSDK?.addSequentialInfo(
                            sequentialInfo
                                .value, sb.toString()
                        )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun release() {
        registerLivenessDetectCallback(null)
        isStop = true
        sdkHandlerIsSuccess = false
        if (service != null) {
            service?.shutdownNow()
            try {
                service?.awaitTermination(100, TimeUnit.MILLISECONDS)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            service = null
        }
        if (nv21Datas != null) {
            nv21Datas = null
        }
    }

    private fun isRootSystem(): Boolean {
        var f: File
        val kSuSearchPaths = arrayOf(
            "/system/bin/", "/system/xbin/",
            "/system/sbin/", "/sbin/", "/vendor/bin/"
        )
        try {
            for (i in kSuSearchPaths.indices) {
                f = File(kSuSearchPaths[i] + "su")
                if (f.exists()) {
                    return true//root
                }
            }
        } catch (e: Exception) {
        }
        return false
    }

}

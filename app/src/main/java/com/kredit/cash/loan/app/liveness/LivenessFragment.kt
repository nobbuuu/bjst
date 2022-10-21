package com.kredit.cash.loan.app.liveness

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.RectF
import android.hardware.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dfsdk.liveness.DFLivenessSDK
import com.dfsdk.liveness.DFLivenessSDK.DFLivenessImageResult
import com.dfsdk.liveness.DFLivenessSDK.DFRect
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.utils.BitmapUtils

/**
 * Created by Win on 2022/10/19
 */
open class LivenessFragment : Fragment(), Camera.PreviewCallback {

    lateinit var mSurfaceView: SurfaceView
    lateinit var mOverlayView: LivenessOverlayView
    lateinit var mCameraBase: LivenessCameraManager
    val mProcess by lazy { LivenessDetector(requireActivity()) }
    lateinit var mWaitDetectView: View
    val sensorManager: SensorManager by lazy { activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    var lastDescribe: String? = null
    lateinit var mTvHintView: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_liveness, container, false)
        initView(view)
        initData()
        return view
    }

    fun initView(view: View) {
        view.apply {
            mWaitDetectView = findViewById(R.id.wait_time_notice)
            mTvHintView = findViewById(R.id.id_tv_silent_hint)
            mSurfaceView = findViewById(R.id.surfaceView)
            mOverlayView = findViewById(R.id.overlayView)
        }
    }

    fun initData() {
        mCameraBase = LivenessCameraManager(requireActivity(), mSurfaceView)
        mCameraBase.setPreviewCallback(this)
        mCameraBase.addPreviewCallbackBuffer()
        mProcess.registerLivenessDetectCallback(mLivenessListener)
        mOverlayView.showBorder()
    }

    override fun onResume() {
        super.onResume()
        listOf(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_ROTATION_VECTOR,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_MAGNETIC_FIELD,
        ).forEach {
            sensorManager.registerListener(
                mSensorEventListener,
                sensorManager.getDefaultSensor(it),
                SensorManager.SENSOR_DELAY_UI
            )
        }
        mProcess.startLiveness()
    }

    override fun onPause() {
        super.onPause()
        mProcess.stopLiveness()
        sensorManager.unregisterListener(mSensorEventListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mProcess.release()
    }

    fun getSilentDetectionRegion(marginScale: Float): RectF {
        val region = RectF()
        val scale = CameraPhysics.previewWidth.toFloat() / mOverlayView.height
        val rect = mOverlayView.mScanRect!!
        region.left = (mOverlayView.height - rect.bottom) * scale
        region.top = (mOverlayView.width - rect.right) * scale
        region.right = region.left + rect.width() * scale
        region.bottom = region.top + rect.height() * scale
        val margin = (rect.width() * marginScale).toInt()
        region.left = region.left - margin
        region.top = region.top - margin
        region.right = region.right + margin
        region.bottom = region.bottom + margin
        return region
    }

    val mLivenessListener = object :
        LivenessDetector.OnLivenessCallBack {
        override fun onStatusCallback(
            livenessStatus: LivenessStatus, livenessEncryptResult: ByteArray?,
            imageResult: Array<DFLivenessImageResult>?
        ) {
            onLivenessDetectCallBack(livenessStatus, livenessEncryptResult, imageResult)
        }

        override fun onDectCallback(value: Int, hasFace: Boolean, faceValid: Boolean, rect: DFRect) {
            onFaceDetectCallback(value, hasFace, faceValid)
        }

        override fun getDetectRegion(): RectF {
            return getSilentDetectionRegion(0.05f)
        }

        override fun getOrientation(): Int {
            return mCameraBase.getCameraOrientation()
        }


        override fun addBuffer() {
            mCameraBase.addPreviewCallbackBuffer()
        }

        override fun onError(error: Int) {
            activity?.finish()
        }
    }

    private fun onFaceDetectCallback(
        value: Int,
        hasFace: Boolean,
        faceValid: Boolean,
    ) {
        if (value == DFLivenessSDK.DFLivenessMotion.HOLD_STILL.value) {
            val color: Int
            val describe: String
            if (!hasFace) {
                color = Color.RED
                describe = "Please place your face inside the circle"
            } else {
                if (!faceValid) {
                    color = Color.RED
                    describe = "Please move away from the screen"
                } else {
                    color = Color.GREEN
                    describe = "Please hold still"
                }
            }
            if (lastDescribe != describe) {
                activity?.runOnUiThread {
                    lastDescribe = describe
                    mOverlayView.showBorder()
                    mOverlayView.setBorderColor(color)
                    mTvHintView.text = lastDescribe
                }
            }
        } else {
            mOverlayView.hideBorder()
        }
    }

    fun onLivenessDetectCallBack(
        status: LivenessStatus,
        livenessEncryptResult: ByteArray?,
        imageResult: Array<DFLivenessImageResult>?
    ) {
        activity?.runOnUiThread {
            when (status) {
                LivenessStatus.Success -> {
                    if (imageResult != null) {
                        for (itemImageResult in imageResult) {
                            val image = itemImageResult.image
                            var cropBitmap: Bitmap?
                            val options = BitmapFactory.Options()
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888
                            cropBitmap =
                                BitmapFactory.decodeByteArray(image, 0, image.size, options)
                            val bmp = BitmapUtils.cropResultBitmap(
                                cropBitmap,
                                CameraPhysics.previewWidth,
                                CameraPhysics.previewHeight,
                                mOverlayView.getScanRectRatio()
                            )
                            itemImageResult.detectImage = BitmapUtils.convertBmpToJpeg(bmp)
                            BitmapUtils.recyleBitmap(cropBitmap)
                            BitmapUtils.recyleBitmap(bmp)
                        }
                    }
                    activity?.apply {
                        val intent=Intent()
                        val result = LivenessResult(livenessEncryptResult,imageResult)
                        intent.putExtra("data",result)
                        setResult(AppCompatActivity.RESULT_OK, intent)
                        finish()
                    }
                }
                LivenessStatus.InitWaiting-> {
                    mWaitDetectView.visibility = View.VISIBLE
                }
                LivenessStatus.EndWaiting -> {
                    mWaitDetectView.visibility = View.GONE
                    mProcess.startLiveness()
                }
            }
        }
    }

    protected var mSensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(arg0: Sensor, arg1: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            mProcess.addSequentialInfo(event.sensor.type, event.values)
        }
    }


    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        mProcess.onPreviewFrame(data, camera)
    }

}
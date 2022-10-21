package com.kredit.cash.loan.app.liveness

import android.content.Context
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.ref.WeakReference

/**
 * Created by Win on 2022/10/20
 */
class LivenessCameraManager(context: Context, val surfaceView: SurfaceView) {
    private var mCamera: Camera? = null
    var mCameraInfo: Camera.CameraInfo? = null
    var mSurfaceHolder: SurfaceHolder
    private val mMatrix = Matrix()
    private var mHasFrontCamera = false
    var mContext: WeakReference<Context>
    private var mChildPreviewCallback: Camera.PreviewCallback? = null
    private var mPreviewCallbackData: ByteArray? = null

    init {
        mContext = WeakReference(context)
        mSurfaceHolder = surfaceView.holder
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        mSurfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int,
                width: Int, height: Int
            ) {
                mMatrix.reset()
                mMatrix.setScale(
                    width / CameraPhysics.previewWidth.toFloat(),
                    height / CameraPhysics.previewHeight.toFloat()
                )
                initCameraParameters()
            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                mCamera = null
                openCamera()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                releaseCamera()
                if (mPreviewCallbackData != null) {
                    mPreviewCallbackData = null
                }
            }
        })
    }

    private fun openCamera() {
        releaseCamera()
        mHasFrontCamera = false
        openCamera(false)
        if (!mHasFrontCamera) {
            openCamera(true)
        }
        try {
            mCamera?.setPreviewDisplay(mSurfaceHolder)
            initCameraParameters()
            initPreviewCallbackData()
            addPreviewCallbackBuffer()
        } catch (ex: Exception) {
            releaseCamera()
            finishActivity()
        }
    }

    private fun initPreviewCallbackData() {
        if (mPreviewCallbackData == null) {
            mPreviewCallbackData = ByteArray(CameraPhysics.previewWidth * CameraPhysics.previewHeight * 3 / 2)
        }
    }

    fun addPreviewCallbackBuffer() {
        mCamera?.addCallbackBuffer(mPreviewCallbackData)
    }

    private fun openCamera(any: Boolean) {
        val info = Camera.CameraInfo()
        for (i in 0 until Camera.getNumberOfCameras()) {
            Camera.getCameraInfo(i, info)
            val front = info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT
            if (any || front) {
                if (front) {
                    mHasFrontCamera = true
                }
                try {
                    mCamera = Camera.open(i)
                    mCameraInfo = info
                } catch (e: RuntimeException) {
                    e.printStackTrace()
                    mCamera?.let {
                        it.release()
                        mCamera = null
                    }
                    continue
                }
                break
            }
        }
    }

    fun getCameraOrientation(): Int {
        return mCameraInfo!!.orientation
    }

    fun finishActivity() {
        mContext.get()?.let {
            (it as LivenessActivity).finish()
        }
    }

    fun setPreviewCallback(previewCallback: Camera.PreviewCallback?) {
        mChildPreviewCallback = previewCallback
    }

    fun initCameraParameters() {
        if (mCamera == null) {
            return
        }
        try {
            val parameters = mCamera!!.parameters
            parameters.previewFormat = ImageFormat.NV21
            CameraPhysics.initCameraPhysics(parameters)
            parameters.setPreviewSize(CameraPhysics.previewWidth, CameraPhysics.previewHeight)
            if (parameters.supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            }
            parameters.sceneMode = Camera.Parameters.SCENE_MODE_AUTO
            if (mContext.get() != null) {
                if (mContext.get()!!.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters["orientation"] = "portrait"
                    parameters["rotation"] = 90
                    if (mCameraInfo!!.facing == Camera.CameraInfo.CAMERA_FACING_FRONT && mCameraInfo!!.orientation == 90) {
                        mCamera!!.setDisplayOrientation(270)
                    } else {
                        mCamera!!.setDisplayOrientation(90)
                    }
                } else {
                    parameters["orientation"] = "landscape"
                    mCamera!!.setDisplayOrientation(0)
                }
            }
            mCamera!!.parameters = parameters
            mCamera!!.setPreviewCallbackWithBuffer(mPreviewCallback)
            mCamera!!.startPreview()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun releaseCamera() {
        mCamera?.let {
            try {
                it.setPreviewCallback(null)
                it.stopPreview()
                it.release()
                mCamera = null
            } catch (e: Exception) {

            }
        }
    }

    var mPreviewCallback =
        Camera.PreviewCallback { data, camera ->
            if (mChildPreviewCallback != null) {
                mChildPreviewCallback!!.onPreviewFrame(data, camera)
            }
        }


}
package com.kredit.cash.loan.app.liveness

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import android.widget.FrameLayout
import com.kredit.cash.loan.app.R


/**
 * Created by Win on 2022/10/21
 */
class LivenessFrameLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val mSurfaceView: SurfaceView by lazy {
        findViewById(R.id.surfaceView)
    }
    val mOverlayView: LivenessOverlayView by lazy {
        findViewById(R.id.overlayView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        val previewWidth = CameraPhysics.previewWidth
        val previewHeight = CameraPhysics.previewHeight

        val h = b - t
        val w = r - l
        val newW = (previewHeight.toFloat() / previewWidth * h).toInt()
        if (newW > w) {
            val newL = -(newW - w) / 2
            val newR = newL + newW
            mSurfaceView.layout(newL, t, newR, b)
            mOverlayView.layout(newL, t, newR, b)
        } else {
            val newH = (previewWidth.toFloat() / previewHeight * w).toInt()
            val newT = -(newH - h) / 2
            val newB = newT + newH
            mSurfaceView.layout(0, newT, w, newB)
            mOverlayView.layout(0, newT, w, newB)
        }
    }
}

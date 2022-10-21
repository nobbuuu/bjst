package com.kredit.cash.loan.app.liveness

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by Win on 2022/10/21
 */
class LivenessOverlayView(context: Context?, attributeSet: AttributeSet?) :
    RelativeLayout(context, attributeSet) {

    private var borderColor = Color.TRANSPARENT
    private var borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mLockedBackgroundPath = Path()
    var mLockedBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mFaceRect: RectF? = null
    var mScanRect: Rect? = null
    private var mIsBorderHidden = true
    private var mCircleRadius = 0
    private var mCircleCenterX = 0
    private var mCircleCenterY = 0

    init {
        setWillNotDraw(false)
        initBackgroundPaint()
        initBorderPaint()
        initCircle()
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    fun initCircle() {
        val displayMetrics = resources.displayMetrics
        val scale = displayMetrics.heightPixels / 1920f
        mCircleRadius = (437 * scale).toInt()
        mCircleCenterY = (319 * scale + mCircleRadius).toInt()
    }

    private fun initBackgroundPaint() {
        mLockedBackgroundPaint.apply {
            clearShadowLayer()
            style = Paint.Style.FILL
            color = Color.WHITE
        }
    }

    private fun initBorderPaint() {
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.clearShadowLayer()
        borderPaint.style = Paint.Style.STROKE
        borderPaint.color = borderColor
        borderPaint.strokeWidth = 14f
    }

    fun setBorderColor(color: Int) {
        if (!mIsBorderHidden) {
            borderColor = color
            borderPaint.color = borderColor
            postInvalidate()
        }
    }

    fun showBorder() {
        mIsBorderHidden = false
        setBorderColor(Color.RED)
    }

    fun hideBorder() {
        mIsBorderHidden = true
        borderColor = Color.TRANSPARENT
        borderPaint.color = borderColor
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mScanRect?.let {
            canvas.drawPath(mLockedBackgroundPath, mLockedBackgroundPaint)
            canvas.drawCircle(
                mCircleCenterX.toFloat(), mCircleCenterY.toFloat(), mCircleRadius.toFloat(),
                borderPaint
            )
        }
    }


    fun getScanRectRatio(): RectF {
        val ratioRectF = RectF()
        ratioRectF.left = mScanRect!!.left.toFloat() / width
        ratioRectF.top = mScanRect!!.top.toFloat() / height
        ratioRectF.right = mScanRect!!.right.toFloat() / width
        ratioRectF.bottom = mScanRect!!.bottom.toFloat() / height
        return ratioRectF
    }

    fun initialInfo() {
        mCircleCenterX = width / 2
        mScanRect = Rect(
            mCircleCenterX - mCircleRadius,
            mCircleCenterY - mCircleRadius,
            mCircleCenterX + mCircleRadius,
            mCircleCenterY + mCircleRadius
        )
        mLockedBackgroundPath.reset()
        mLockedBackgroundPath.addRect(
            RectF(
                left.toFloat(),
                top.toFloat(), right.toFloat(), bottom.toFloat()
            ), Path.Direction.CCW
        )
        mLockedBackgroundPath.addCircle(
            mCircleCenterX.toFloat(),
            mCircleCenterY.toFloat(),
            mCircleRadius.toFloat(),
            Path.Direction.CW
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        initialInfo()
        invalidate()
    }

}

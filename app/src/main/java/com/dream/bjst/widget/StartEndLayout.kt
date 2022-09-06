package com.dream.bjst.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R
import com.tcl.base.kt.sp

class StartEndLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var mStartTv: TextView
    var mEndTv: TextView? = null
    var mEndEdt: EditText? = null

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.startEndLayout)
        when (array.getInteger(R.styleable.startEndLayout_viewType, 1)) {
            1 -> {
                LayoutInflater.from(context).inflate(R.layout.view_lr_custom_view, this)
            }
            2 -> {
                LayoutInflater.from(context).inflate(R.layout.view_lr_edt_view, this)
            }
        }
        val startText = array.getString(R.styleable.startEndLayout_startText)
        val startTextSize = array.getDimension(R.styleable.startEndLayout_startTextSize, 15.sp)
        val startTextColor =
            array.getInteger(R.styleable.startEndLayout_startTextColor, R.color.colorBlack666)
        val endText = array.getString(R.styleable.startEndLayout_endText)
        val hintText = array.getString(R.styleable.startEndLayout_hintText)
        val endTextSize = array.getDimension(R.styleable.startEndLayout_endTextSize, 18.sp)
        val endTextColor = array.getInteger(R.styleable.startEndLayout_endTextColor, R.color.black)
        val isDivider = array.getBoolean(R.styleable.startEndLayout_isDivider, true)
        val isSelect = array.getBoolean(R.styleable.startEndLayout_isSelect, false)
        mStartTv = findViewById(R.id.startTv)
        mStartTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, startTextSize)
        mStartTv.setTextColor(startTextColor)
        mStartTv.text = "$startText"
        mEndTv = findViewById(R.id.endTv)
        mEndEdt = findViewById(R.id.endEdt)
        mEndTv?.setTextSize(TypedValue.COMPLEX_UNIT_PX, endTextSize)
        mEndEdt?.setTextSize(TypedValue.COMPLEX_UNIT_PX, endTextSize)
        mEndTv?.setTextColor(endTextColor)
        mEndEdt?.setTextColor(endTextColor)
        mEndTv?.text = "$endText"
        mEndEdt?.setText(endText)
        mEndEdt?.hint = hintText
        val mDivider = findViewById<View>(R.id.divider)
        mDivider.isVisible = isDivider
        val mSelectIv = findViewById<ImageView>(R.id.nextIv)
        mSelectIv.isVisible = isSelect
    }

    fun setStartText(text: String) {
        mStartTv.text = text
    }

    fun setEndText(text: String) {
        mEndTv?.text = text
    }

    fun setEndEdtText(text: String) {
        mEndEdt?.setText(text)
    }

    fun setEndEdtTextColor(color: Int) {
        mEndEdt?.setTextColor(ColorUtils.getColor(color))
    }

    fun setEndTextColor(color: Int) {
        mEndTv?.setTextColor(ColorUtils.getColor(color))
    }

    fun getEndEdtText(): String {
        return mEndEdt?.text.toString().trim()
    }

    fun getEndText(): String {
        return mEndTv?.text.toString().trim()
    }
}
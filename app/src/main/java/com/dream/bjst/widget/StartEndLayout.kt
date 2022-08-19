package com.dream.bjst.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.dream.bjst.R
import com.tcl.base.kt.sp

class StartEndLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_lr_custom_view, this)
        val array = context.obtainStyledAttributes(attrs, R.styleable.startEndLayout)
        val startText = array.getString(R.styleable.startEndLayout_startText)
        val startTextSize = array.getInteger(R.styleable.startEndLayout_startTextSize, 15)
        val startTextColor = array.getInteger(R.styleable.startEndLayout_startTextColor, R.color.colorBlack666)
        val endText = array.getString(R.styleable.startEndLayout_endText)
        val endTextSize = array.getInteger(R.styleable.startEndLayout_endTextSize, 18)
        val endTextColor = array.getInteger(R.styleable.startEndLayout_endTextColor, R.color.black)
        val isDivider = array.getBoolean(R.styleable.startEndLayout_isDivider, true)
        val mStartTv = findViewById<TextView>(R.id.startTv)
        mStartTv.textSize = startTextSize.sp
        mStartTv.setTextColor(startTextColor)
        mStartTv.text = "$startText："
        val mEndTv = findViewById<TextView>(R.id.endTv)
        mEndTv.textSize = endTextSize.sp
        mEndTv.setTextColor(endTextColor)
        mEndTv.text = "$endText："
        val mDivider = findViewById<View>(R.id.divider)
        mDivider.isVisible = isDivider
    }
}
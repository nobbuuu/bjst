package com.dream.bjst.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dream.bjst.R
import com.tcl.base.kt.nullToEmpty

class LrKvTextview @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mTitleTv: TextView
    private var mContentTv: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_kv_textview, this)
        val array = context.obtainStyledAttributes(attrs, R.styleable.LrKvView)
        val title = array.getString(R.styleable.LrKvView_lrTitle)
        val content = array.getString(R.styleable.LrKvView_lrText)
        mTitleTv = findViewById(R.id.title)
        mTitleTv.text = "$title："
        mContentTv = findViewById(R.id.contentTV)
        mContentTv.text = content
    }

    fun setTitle(title: String?) {
        mTitleTv.text = title.nullToEmpty()
    }

    fun setContent(content: String?) {
        mContentTv.text = content.nullToEmpty()
    }

    fun getContent() : String{
        return mContentTv.text.toString().trim()
    }

    fun clear() {
        mContentTv.text = ""
    }
}

@BindingAdapter("lrText")
fun wmsText(view: LrKvTextview, s: String?) {
    s?.run {
        view.setContent(this)
    }
}
package com.dream.bjst.main

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.RenderMode
import com.dream.bjst.R
import com.dream.bjst.main.menu.TabItem

class MainTabView @JvmOverloads constructor(
    context: Context, val item: TabItem, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {
    private var lottieIcon: LottieAnimationView
    private val tvName: TextView
    private val tvUnread: TextView
    private val tvRedPoint: View
    private var tabImagIv: ImageView

    init {
        gravity = Gravity.CENTER
        val rootView = View.inflate(context, R.layout.view_main_tab, null)
        lottieIcon = rootView.findViewById(R.id.lottieView)
        tabImagIv = rootView.findViewById(R.id.tabImgIv)
        tvName = rootView.findViewById(R.id.tabTitle)
        tvUnread = rootView.findViewById(R.id.tabUnreadCount)
        tvRedPoint = rootView.findViewById(R.id.tabUnread)
        initData()
        addView(rootView)
    }


    private fun initData() {
        lottieIcon.setRenderMode(RenderMode.HARDWARE)
        lottieIcon.setAnimation(item.lottieFile)
//        lottieIcon.id = item.id
        tvName.setText(item.text)
        when {
            item.unReadCount > 0 -> {
                tvUnread.text = item.unReadCount.toString()
                tvUnread.visibility = View.VISIBLE
            }
            item.unRead -> {
                tvRedPoint.visibility = View.VISIBLE
            }
            else -> {
                tvRedPoint.visibility = View.GONE
                tvUnread.visibility = View.GONE
            }
        }
    }

    fun updateBadge(count: Int) {
        if (count > 0) {
            tvUnread.text = if (count > 99) {
                "99+"
            } else {
                count.toString()
            }
            tvUnread.visibility = View.VISIBLE
        } else {
            tvUnread.visibility = View.GONE
        }
    }

    fun updateRedPoint(unread: Boolean) {
        tvRedPoint.visibility = if (unread) View.VISIBLE else View.GONE

    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        try {
            if (selected) {
                tvName.setTextColor(ContextCompat.getColor(context, R.color.regular_green))
                tabImagIv.setImageResource(item.selectImg)
                lottieIcon.playAnimation()
            } else {
                lottieIcon.cancelAnimation()
                lottieIcon.frame = 0
                tvName.setTextColor(ContextCompat.getColor(context, R.color.text_norm_black))
                tabImagIv.setImageResource(item.normalImg)
            }
        } catch (ignored: Exception) {
        }
    }
}
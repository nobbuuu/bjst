package com.dream.bjst.account.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.webkit.WebView
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.R
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.Constant
import com.dream.bjst.databinding.ActivityPrivacyBinding
import com.dream.bjst.home.HomeImgActivity
import com.dream.bjst.login.LoginActivity
import com.dream.bjst.main.MainActivity
import com.dream.bjst.other.WebViewActivity
import com.ruffian.library.widget.RCheckBox
import com.ruffian.library.widget.RImageView
import com.ruffian.library.widget.RTextView
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.utils.MmkvUtil

/**
 * 创建日期：2022-09-05 on 0:17
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class PrivacyActivity : BaseActivity<AccountViewModel, ActivityPrivacyBinding>() {
    //弹窗控件
    var popupWindow: PopupWindow? = null
    var actionType: Int = -1
    override fun initView(savedInstanceState: Bundle?) {
        actionType = intent.getIntExtra("actionType", -1)
        event()
    }

    override fun initData() {
    }

    override fun initDataOnResume() {
        viewModel.privacy()
        //利用H5给网络协议添加超链接
        callService(
            "Accept Terms & Conditions and Privacy Policy and to receive notification from SMS and email",
            mBinding.privacyPolicyTv
        )
    }

    /**
     * 获取数据
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.privacyResult.observe(this) {
            mBinding.webView.loadUrl(it.`849186999D87879D9B9AB18C8498959D9AA18698`)
            showPopWindow()
        }
    }

    /**
     * 事假函数相应
     */

    fun event() {
        mBinding.privacyCb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mBinding.privacyCheckButton.text = "Continue"
            } else {
                mBinding.privacyCheckButton.text = "Please check this box and continue"
            }
        }
        //check按钮
        mBinding.privacyCheckButton.ktClick {
            if (!mBinding.privacyCb.isChecked) {
                ToastUtils.showShort("Please check this box and continue")
            } else {
                MmkvUtil.encode("isFirst", true)
                ktStartActivity(MainActivity::class) {
                    putExtra("actionType", Constant.ACTION_TYPE_HOME)
                }
                finish()
            }
        }

        //disagree
        mBinding.privacyDisagreeButton.setOnClickListener(View.OnClickListener {
            if (actionType == 1) {
                ktStartActivity(HomeImgActivity::class)
            }
        })
    }

    /**
     * 显示弹窗方法
     */
    @SuppressLint("ResourceType")
    private fun showPopWindow() {
        // 加载弹窗布局
        val pwView =
            LayoutInflater.from(this).inflate(R.layout.item_popupwindow_privacy, null, false)
        // 实例化 PopupWindow
        popupWindow = PopupWindow(
            pwView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (actionType == 1) {
            popupWindow?.isOutsideTouchable = false
            popupWindow?.isFocusable = false
        }
        val webView = pwView.findViewById<WebView>(R.id.webView)
        viewModel.privacyResult.value?.`84869D8295978DB59386919199919A80B68691829D95868DA18698`?.let {
            webView.loadUrl(it)
        }

        //设置关闭popup按钮
        val closeImage = pwView.findViewById<RImageView>(R.id.privacy_fault_image)
        closeImage.setOnClickListener {
            popupWindow?.dismiss()
        }
        //设置popupWindow里面的未选中按钮
        val unselectImage = pwView.findViewById<RCheckBox>(R.id.privacyPopupCb)
        val checkButton = pwView.findViewById<RTextView>(R.id.privacy_box_button)
        unselectImage.isChecked = mBinding.privacyCb.isChecked
        //设置continue按钮
        checkButton.ktClick {
            if (!unselectImage.isChecked) {
                ToastUtils.showShort("Please check this box and continue")
            } else {
                val isFirst = MmkvUtil.decodeBooleanOpen("isFirst")
                if (isFirst == true) {
                    ktStartActivity(MainActivity::class) {
                        putExtra(Constant.actionType, Constant.ACTION_TYPE_HOME)
                    }
                    MmkvUtil.encode("isFirst", true)
                }
                finish()
            }
        }
        //设置disagree按钮
        val disagreeButton = pwView.findViewById<RTextView>(R.id.privacy_dis_agree_button)
        disagreeButton?.setOnClickListener {
            popupWindow?.dismiss()
            if (actionType == 1) {
                ktStartActivity(HomeImgActivity::class)
            }
        }
        // 设置 popupWindow
        popupWindow?.isFocusable = true // 取得焦点
        //点击外部消失
        popupWindow?.isOutsideTouchable = true
        //设置可以点击
        popupWindow?.isTouchable = true
        // 加载弹窗动画
        popupWindow?.animationStyle = R.style.pw_bottom_anim_style
        //从底部显示
        popupWindow?.showAtLocation(pwView, Gravity.BOTTOM, 0, 0)
        windowAlpha(0.5f)
        // 设置弹窗关闭监听——恢复亮度
        popupWindow?.setOnDismissListener {
            windowAlpha(1f)
            mBinding.privacyCb.isChecked = unselectImage.isChecked
        }
    }

    // 控制背景亮度
    private fun windowAlpha(alpha: Float) {
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }

    /**
     * @param content  文字内容
     * @param textView 加载文字的textview
     */
    private fun callService(content: String, textView: TextView) {
        val builder = SpannableStringBuilder(content)
        //设置Terms&Conditions链接
        val i = content.indexOf("T") //截取文字开始的下标
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                viewModel.privacyResult.value?.`8691939D87809186B59386919199919A80A18698`?.let {
                    ktStartActivity(WebViewActivity::class) {
                        putExtra("webUrl", it)
                    }
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = resources.getColor(R.color.greed_4E) //设置文字颜色
                ds.isUnderlineText = true //设置下划线//根据需要添加
            }
        }, i, i + 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置Privacy policy链接
        val m = content.indexOf("P") //截取文字开始的下标
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                //完整隐私协议
                viewModel.privacyResult.value?.`84869D8295978DB59386919199919A80A18698`?.let {
                    ktStartActivity(WebViewActivity::class) {
                        putExtra("webUrl", it)
                    }
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = resources.getColor(R.color.greed_4E) //设置文字颜色
                ds.isUnderlineText = true //设置下划线//根据需要添加
            }
        }, m, m + 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明，否则会一直出现高亮
        textView.text = builder
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

}
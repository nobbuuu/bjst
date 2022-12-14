package com.kredit.cash.loan.app.account.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.webkit.WebView
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.account.vm.AccountViewModel
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.databinding.ActivityPrivacyBinding
import com.kredit.cash.loan.app.home.HomeImgActivity
import com.kredit.cash.loan.app.main.MainActivity
import com.kredit.cash.loan.app.other.WebViewActivity
import com.ruffian.library.widget.RCheckBox
import com.ruffian.library.widget.RImageView
import com.ruffian.library.widget.RTextView
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow
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


    /**
     * 申请隐私弹窗
     */
    fun getPermissions() {
        PermissionUtils.permission(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    if (granted.size == 9) {
                        goMain()
                    }
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    "The authorization can be used normally".ktToastShow()
                }

            }).request()
    }

    /**
     * 跳转到应用程序信息
     */
    fun gotoAppDetail(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun initData() {

        viewModel.privacy()
        //利用H5给网络协议添加超链接
        callService(
            "Accept Terms & Conditions and Privacy Policy and to receive notification from SMS and email",
            mBinding.privacyPolicyTv
        )
    }

    override fun initDataOnResume() {


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
                getPermissions()
            }
        }

        //disagree
        mBinding.privacyDisagreeButton.setOnClickListener(View.OnClickListener {
            ktStartActivity(HomeImgActivity::class)
        })
    }

    private fun goMain() {
        mBinding.webView.post {
            val isFirst = MmkvUtil.decodeBooleanOpen("isFirst")
            if (isFirst == true) {
                ktStartActivity(MainActivity::class) {
                    putExtra(Constant.actionType, Constant.ACTION_TYPE_HOME)
                }
                MmkvUtil.encode("isFirst", false)
            }
            finish()
        }
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
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        popupWindow?.isOutsideTouchable = false
        popupWindow?.isFocusable = false
        val webView = pwView.findViewById<WebView>(R.id.webView)
        viewModel.privacyResult.value?.`84869D8295978DB59386919199919A80B68691829D95868DA18698`?.let {
            webView.loadUrl(it)
        }

        //设置关闭popup按钮
        val closeImage = pwView.findViewById<RImageView>(R.id.privacy_fault_image)
        closeImage.setOnClickListener {
            popupWindow?.dismiss()
            finish()
        }
        //设置popupWindow里面的未选中按钮
        val unselectImage = pwView.findViewById<RCheckBox>(R.id.privacyPopupCb)
        val checkButton = pwView.findViewById<RTextView>(R.id.privacy_box_button)
        //设置continue按钮
        checkButton.ktClick {
            if (!unselectImage.isChecked) {
                ToastUtils.showShort("Please check this box and continue")
            } else {
                popupWindow?.dismiss()
            }
        }
        //设置disagree按钮
        val disagreeButton = pwView.findViewById<RTextView>(R.id.privacy_dis_agree_button)
        disagreeButton?.setOnClickListener {
            popupWindow?.dismiss()
            finish()
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
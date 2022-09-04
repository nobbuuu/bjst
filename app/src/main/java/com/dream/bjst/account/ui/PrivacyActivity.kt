package com.dream.bjst.account.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.R
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.databinding.ActivityPrivacyBinding
import com.ruffian.library.widget.RImageView
import com.tcl.base.common.ui.BaseActivity

/**
 * 创建日期：2022-09-05 on 0:17
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class PrivacyActivity :BaseActivity<AccountViewModel,ActivityPrivacyBinding>(){
    //弹窗控件
    var popupWindow: PopupWindow? = null
    var pwView: View? = null
    var isClick = false

    override fun initView(savedInstanceState: Bundle?) {

        //利用H5给网络协议添加超链接
        callService("Accept Terms & Conditions and Privacy Policy and to receive notification from SMS and email",
            mBinding.privacyPolicyTv
        )
        event()
    }
      fun event() {
        //勾选按钮
        mBinding.privacyUnselectImage.setOnClickListener(View.OnClickListener {
            isClick = if (isClick == false) {
                mBinding.privacyUnselectImage.setImageResource(R.mipmap.select)
                true
            } else {
                mBinding.privacyUnselectImage.setImageResource(R.mipmap.unselect)
                false
            }
        })
        //check按钮
        mBinding.privacyCheckButton.setOnClickListener(View.OnClickListener {
            if (isClick == false) {
                ToastUtils.showShort("Please check this box and continue")
            } else {
                ToastUtils.showShort("检查中...")
            }
        })
        //disagree
        mBinding.privacyDisagreeButton.setOnClickListener(View.OnClickListener { onBackPressed() })
        //测试弹窗
    }

    /**
     * 显示弹窗方法
     */
    @SuppressLint("ResourceType")
    private fun showPopWindow() {
        // 加载弹窗布局
        pwView = LayoutInflater.from(this).inflate(R.layout.item_popupwindow_privacy, null, false)
        // 实例化 PopupWindow
        popupWindow = PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //设置关闭popup按钮
        val closeImage = pwView!!.findViewById<RImageView>(R.id.privacy_fault_image)
        closeImage.setOnClickListener {
            popupWindow!!.dismiss()
            windowAlpha(1f)
        }
        //设置popupWindow里面的未选中按钮
        val unselectImage = pwView!!.findViewById<RImageView>(R.id.privacy_popup_unselect_image)
        unselectImage.setOnClickListener {
            isClick = if (isClick == false) {
                unselectImage.setImageResource(R.mipmap.unselect)
                true
            } else {
                unselectImage.setImageResource(R.mipmap.select)
                false
            }
        }
        //设置continue按钮
        val checkButton = pwView!!.findViewById<Button>(R.id.privacy_box_button)
        checkButton.setOnClickListener {
            if (isClick == true) {
                ToastUtils.showShort("Please check this box and continue")
            } else {
                ToastUtils.showShort("请测试登录")
            }
        }
        //设置disagree按钮
        val disagreeButton = pwView!!.findViewById<Button>(R.id.privacy_dis_agree_button)
        disagreeButton.setOnClickListener { popupWindow!!.dismiss() }
        // 设置 popupWindow
        popupWindow!!.isFocusable = true // 取得焦点
        //点击外部消失
        popupWindow!!.isOutsideTouchable = true
        //设置可以点击
        popupWindow!!.isTouchable = true
        // 加载弹窗动画
        popupWindow!!.animationStyle = R.style.pw_bottom_anim_style
        //从底部显示
        popupWindow!!.showAtLocation(pwView, Gravity.BOTTOM, 0, 0)
        windowAlpha(0.5f)
        // 设置弹窗关闭监听——恢复亮度
        popupWindow!!.setOnDismissListener { windowAlpha(1f) }
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

                // 要跳转的链接
                val uri = Uri.parse("https://baidu.com")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
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

                // 要跳转的链接
//                Uri uri = Uri.parse("https://baidu.com");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
                showPopWindow()
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

    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
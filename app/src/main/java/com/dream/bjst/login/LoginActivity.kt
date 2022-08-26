package com.dream.bjst.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Looper.getMainLooper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.R
import com.dream.bjst.bean.PhoneCodeParam
import com.dream.bjst.databinding.ActivityLoginBinding
import com.dream.bjst.identification.IdentificationActivity
import com.dream.bjst.loan.vm.LoginViewModel
import com.dream.bjst.main.MainActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    var isSendCode = false
    val mHandler = Handler(Looper.getMainLooper())
    var countdown = 60
    var codeType = 0
    override fun initView(savedInstanceState: Bundle?) {

        mBinding.nextTv.ktClick {
            if (!isSendCode) {//获取验证码
                mBinding.phoneLay.isVisible = false
                mBinding.codeLay.isVisible = true
                mBinding.horiProgress.progress = 100
                isSendCode = true
            } else {//登录
                val phone = mBinding.phoneEdt.text.toString()
                val code = mBinding.codeEdt.text.toString()
                if (code.isNotEmpty()) {
                    viewModel.login(phone, code)
                } else {
                    "Obtain the verification code first".ktToastShow()
                }
            }
        }

        mBinding.resendTv.ktClick {
            codeType = 1
            when (mBinding.resendTv.text.toString()) {
                "Send OTP" -> {
                    sendCode()
                }
                "Resend OTP" -> {
                    sendCode()
                }
                else -> {

                }
            }
        }

        mBinding.voiceTv.ktClick {
            codeType = 2
            when (mBinding.voiceTv.text.toString()) {
                "Try Voice  OTP" -> {
                    sendCode()
                }
                "Resend" -> {
                    sendCode()
                }
                else -> {

                }
            }
        }

        initPolicyUi()
    }

    private val mRunnable = object : Runnable {
        override fun run() {
            if (countdown >= 0) {
                when (codeType) {
                    1 -> {
                        mBinding.resendTv.text = countdown--.toString() + "S"
                    }
                    2 -> {
                        mBinding.voiceTv.text = countdown--.toString() + "S"
                    }
                }
                mHandler.postDelayed(this, 1000)
            } else {
                countdown = 60
                when (codeType) {
                    1 -> {
                        mBinding.resendTv.text = "Resend OTP"
                    }
                    2 -> {
                        mBinding.voiceTv.text = "Resend"
                    }
                }
            }
        }
    }

    private fun sendCode() {
        var phone = mBinding.phoneEdt.text.toString()
        if (phone.isNotEmpty() && phone.length == 10 || phone.length == 11) {
            if (phone[0].equals("0")) {
                phone = phone.substring(1)
            }
            val param = GsonUtils.toJson(
                PhoneCodeParam(
                    `978187809B999186B99B969D9891` = phone,
                    `9D87A29B9D9791` = codeType == 2
                )
            )
            viewModel.sendCode(param)
            mHandler.post(mRunnable)
        } else {
            "please input mobile number".ktToastShow()
        }
    }

    private fun initPolicyUi() {
        val preStr = "Agree"
        val policy1 = "《User services》"
        val policy2 = "《Privacy Policy》"
        val stringBuilder = SpannableStringBuilder(preStr)
        stringBuilder.append(policy1).append("&").append(policy2)
        stringBuilder.setSpan(
            object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    updateDealUi(ds)
                }

                override fun onClick(widget: View) {
                    //跳转用户协议

                }
            }, preStr.length, preStr.length + policy1.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        stringBuilder.setSpan(
            object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    updateDealUi(ds)
                }

                override fun onClick(widget: View) {
                    //跳转隐私政策

                }
            }, preStr.length + policy1.length + 1, stringBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mBinding.privacyPolicyTv.text = stringBuilder
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.sendCode.observe(this) {

        }
        viewModel.loginResult.observe(this) {
            ktStartActivity(IdentificationActivity::class)
        }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }

    private fun updateDealUi(ds: TextPaint) {
        ds.color = ColorUtils.getColor(R.color.colorAccent)
        ds.isUnderlineText = false
    }
}
package com.dream.bjst.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.RegexUtils
import com.dream.bjst.BuildConfig
import com.dream.bjst.R
import com.dream.bjst.bean.PhoneCodeParam
import com.dream.bjst.common.Constant
import com.dream.bjst.common.MmkvConstant
import com.dream.bjst.common.vm.DeviceInfoViewModel
import com.dream.bjst.databinding.ActivityLoginBinding

import com.dream.bjst.dialog.VoiceDialog
import com.dream.bjst.main.MainActivity
import com.dream.bjst.net.Configs
import com.dream.bjst.net.Url
import com.dream.bjst.other.WebViewActivity
import com.dream.bjst.utils.SendCodeUtils
import com.lxj.xpopup.XPopup
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow
import com.tcl.base.utils.MmkvUtil
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    private var isSendCode = false
    private val sendList = arrayListOf<SendCodeUtils>()
    private var codeType = 0

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.nextTv.ktClick {
            if (mBinding.loginCb.isChecked) {
                val phone = mBinding.phoneEdt.text.toString()
                val reg = "^([0][1-9]\\d{9})|([1-9]\\d{9})\$"
                if (!isSendCode) {//获取验证码
                    if (RegexUtils.isMatch(reg, phone)) {
                        mBinding.phoneLay.isVisible = false
                        mBinding.codeLay.isVisible = true
                        mBinding.horiProgress.progress = 100
                        isSendCode = true
                    } else {
                        "Please enter the correct cell phone number".ktToastShow()
                    }
                } else {//登录
                    val code = mBinding.codeEdt.text.toString()
                    if (code.isNotEmpty()) {
                        viewModel.login(phone, code)
                    } else {
                        "Obtain the verification code first".ktToastShow()
                    }
                }
            } else {
                "Please check this box and continue.".ktToastShow()
            }
        }

        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }

        mBinding.resendTv.ktClick {
            codeType = 1
            sendCode()
        }

        mBinding.voiceTv.ktClick {
            codeType = 2
            sendCode()
            VoiceDialog(this) {

            }.show()
        }
        initPolicyUi()
        mBinding.btnDebug.run {
            visibility =
                if (BuildConfig.BUILD_TYPE == "debug") View.VISIBLE else View.GONE
            text = when (Configs.curAppType) {
                Configs.APP_DEV_TYPE -> "当前处于dev环境"
                Configs.APP_TEST_TYPE -> "当前处于sit环境"
                Configs.APP_UAT_TYPE -> "当前处于uat环境"
                Configs.APP_TEST_TYPE_20222 -> "test-20222"
                Configs.APP_TEST_TYPE_20022 -> "test-20022"
                Configs.APP_TEST_TYPE_20002 -> "test-20002"
                else -> "非测试环境"
            }
            ktClick {
                showDebugDialog(this@LoginActivity)
            }
        }
    }

    override fun onBackPressed() {
        if (isSendCode) {
            mBinding.phoneLay.isVisible = true
            mBinding.codeLay.isVisible = false
            mBinding.horiProgress.progress = 50
            isSendCode = false
            mBinding.codeEdt.setText("")
            sendList.forEach {
                it.onPause()
            }
            sendList.clear()
        }else{
            finish()
        }
    }
    private fun showDebugDialog(context: Context) {
        val list = arrayOf(
            Configs.URL_APP_TEST_20222,
            Configs.URL_APP_TEST_20022,
            Configs.URL_APP_TEST_20002,
        )
        XPopup.Builder(context)
            .asBottomList(
                "选择环境",
                list
            ) { position, text ->
                when (position) {
                    0 -> {
                        Configs.curAppType = Configs.APP_TEST_TYPE_20222
                    }
                    1 -> {
                        Configs.curAppType = Configs.APP_TEST_TYPE_20022
                    }
                    2 -> {
                        Configs.curAppType = Configs.APP_TEST_TYPE_20002
                    }
                }
                MmkvUtil.encode(MmkvConstant.KEY_DEBUG_CURRENT_TYPE, Configs.curAppType)
                lifecycleScope.launch {
                    Url.baseUrl = Configs.getAppBaseUrl()
                }
                "$text 切换成功".ktToastShow()
                AppUtils.relaunchApp(true)

            }.show()
    }

    override fun onPause() {
        super.onPause()
        sendList.forEach {
            it.onPause()
        }
    }

    private fun sendCode() {
        val phone = mBinding.phoneEdt.text.toString()
        val reg = "^([0][1-9]\\d{9})|([1-9]\\d{9})\$"
        if (RegexUtils.isMatch(reg, phone)) {
            val param = GsonUtils.toJson(
                PhoneCodeParam(
                    `978187809B999186B99B969D9891` = phone,
                    `9D87A29B9D9791` = codeType == 2
                )
            )
            viewModel.sendCode(param)
        } else {
            "Please enter the correct cell phone number".ktToastShow()
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
                    //跳转注册协议
                    viewModel.privacyResult.value?.`8691939D87809186B59386919199919A80A18698`?.let {
                        ktStartActivity(WebViewActivity::class) {
                            putExtra("webUrl", it)
                        }
                    }
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
                    viewModel.privacyResult.value?.`84869D8295978DB59386919199919A80A18698`?.let {
                        ktStartActivity(WebViewActivity::class) {
                            putExtra("webUrl", it)
                        }
                    }
                }
            }, preStr.length + policy1.length + 1, stringBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mBinding.privacyPolicyTv.movementMethod = LinkMovementMethod.getInstance()
        mBinding.privacyPolicyTv.highlightColor = Color.TRANSPARENT
        mBinding.privacyPolicyTv.text = stringBuilder
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.sendCode.observe(this) {
            val tv = if (codeType == 1) mBinding.resendTv else mBinding.voiceTv
            val str = if (codeType == 1) "Resend OTP" else "Resend"
            sendList.add(SendCodeUtils(tv, Handler(mainLooper), str).start())
            mBinding.codeEdt.postDelayed({
                mBinding.codeEdt.setText("123456")
            }, 1000)
        }
        viewModel.loginResult.observe(this) {
            viewModel.updateDeviceInfo()
            viewModel.fetchCustomerKycStatus()
        }

        viewModel.idCardStatus.observe(this) {
            var action = Constant.ACTION_TYPE_HOME
            if (it.`959898BD809199A4958787`) {
                action = Constant.ACTION_TYPE_MAIN
            }
            ktStartActivity(MainActivity::class) {
                putExtra(Constant.actionType, action)
            }
            finish()
        }

        viewModel.upDevicePhoto.observe(this) {

        }

    }

    override fun initData() {
        viewModel.privacy()
    }

    override fun initDataOnResume() {

    }

    private fun updateDealUi(ds: TextPaint) {
        ds.color = ColorUtils.getColor(R.color.colorAccent)
        ds.isUnderlineText = false
    }
}
package com.dream.bjst.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.R
import com.dream.bjst.bean.PhoneCodeParam
import com.dream.bjst.databinding.ActivityLoginBinding
import com.dream.bjst.loan.vm.LoginViewModel
import com.dream.bjst.main.MainActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun initStateBar(stateBarColor: Int, isLightMode: Boolean, fakeView: View?) {
        super.initStateBar(ColorUtils.getColor(R.color.white), true, mBinding.titleBar)
    }

    var isSendCode = false
    override fun initView(savedInstanceState: Bundle?) {

        mBinding.nextTv.ktClick {
            if (!isSendCode) {//获取验证码
                val phone = mBinding.phoneEdt.text.toString()
                if (phone.isNotEmpty() && phone.length == 10) {
                    val param =
                        GsonUtils.toJson(PhoneCodeParam(`978187809B999186B99B969D9891` = phone))
                    viewModel.sendCode(param)
                } else {
                    "please input 10 digit mobile number".ktToastShow()
                }
            } else {//登录
                val phone = mBinding.phoneEdt.text.toString()
                val code = mBinding.codeEdt.text.toString()
                viewModel.login(phone, code)
            }
        }

    }

    override fun startObserve() {
        super.startObserve()
        viewModel.sendCode.observe(this) {
            isSendCode = it
            mBinding.phoneLay.isVisible = !it
            mBinding.codeLay.isVisible = it
        }
        viewModel.loginResult.observe(this) {
            ktStartActivity(MainActivity::class)
        }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }
}
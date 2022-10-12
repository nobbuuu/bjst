package com.kredit.cash.loan.app.account.ui

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.kredit.cash.loan.app.BuildConfig
import com.kredit.cash.loan.app.account.vm.AccountViewModel
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.databinding.ActivityAccountSettingBinding
import com.kredit.cash.loan.app.login.LoginActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class AccountSettingActivity : BaseActivity<AccountViewModel, ActivityAccountSettingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        //获取标题栏的返回按钮
        mBinding.accountMySettingTitle.leftView.setOnClickListener(View.OnClickListener { onBackPressed() })
        //获取安卓版本号
        mBinding.settingVersion.text = (BuildConfig.VERSION_NAME)
        //获取Email
        mBinding.accountEmail.text = UserManager.getCustomerEmail().ifEmpty { "ajsdklfjksldafa@gmil" }
        //设置用户姓名
        mBinding.accountName.text = UserManager.getUserName().ifEmpty { "User name" }
        //设置电话号码
        mBinding.accountPhone.text = UserManager.getUserPhone().ifEmpty { "Login"}
        event()

    }

    private fun event() {
        mBinding.logOutBtn.ktClick {
            UserManager.clearUserInfo()
            ktStartActivity(LoginActivity::class)
            ActivityUtils.finishAllActivities()
        }
        mBinding.accountPhone.ktClick {
            if (!UserManager.isLogin()){
                ktStartActivity(LoginActivity::class)
            }
        }

    }

    override fun initDataOnResume() {
    }
}
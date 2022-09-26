package com.dream.bjst.account.ui

import android.os.Bundle
import android.view.View
import com.dream.bjst.BuildConfig
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.Constant
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityAccountSettingBinding
import com.dream.bjst.home.HomeActivity
import com.dream.bjst.login.LoginActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.text
import com.tcl.base.utils.MmkvUtil

class AccountSettingActivity :BaseActivity<AccountViewModel,ActivityAccountSettingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        //获取标题栏的返回按钮
        mBinding.accountMySettingTitle.leftView.setOnClickListener(View.OnClickListener { onBackPressed() })
        //获取安卓版本号
        mBinding.settingVersion.text=(BuildConfig.VERSION_NAME)
        //获取Email
        mBinding.accountEmail.text=UserManager.getCustomerEmail()
        //设置用户姓名
        mBinding.accountName.text=UserManager.getUserName()
        //设置电话号码
        mBinding.accountPhone.text=UserManager.getUserPhone()
        event()

    }

    private fun event() {
        mBinding.logOutBtn.ktClick {
            UserManager.clearUserInfo()
            ktStartActivity(LoginActivity::class)
        }


    }

    override fun initDataOnResume() {
    }
}
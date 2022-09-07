package com.dream.bjst.account.ui

import android.os.Bundle
import android.view.View
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.databinding.ActivityAccountSettingBinding
import com.tcl.base.common.ui.BaseActivity

class AccountSettingActivity :BaseActivity<AccountViewModel,ActivityAccountSettingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        //获取标题栏的返回按钮
        mBinding.accountMySettingTitle.leftView.setOnClickListener(View.OnClickListener { onBackPressed() })
        //获取安卓版本号
        mBinding.settingVersion.text=(getVersionCode().toString() + ". 0. 0")
    }

    override fun initDataOnResume() {
    }
}
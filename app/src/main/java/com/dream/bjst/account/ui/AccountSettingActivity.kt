package com.dream.bjst.account.ui

import android.os.Bundle
import android.view.View
import com.dream.bjst.account.vm.AccountSettingModel
import com.dream.bjst.databinding.ActivityAccountSettingBinding
import com.tcl.base.common.ui.BaseActivity

class AccountSettingActivity :BaseActivity<AccountSettingModel,ActivityAccountSettingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        //获取标题栏的返回按钮

        //获取标题栏的返回按钮
        mBinding.accountMySettingTitle.getLeftView().setOnClickListener(View.OnClickListener { onBackPressed() })
        //获取安卓版本号
        //获取安卓版本号
        mBinding.settingVersion.setText(getVersionCode().toString() + ". 0. 0")
    }

    override fun initDataOnResume() {
        TODO("Not yet implemented")
    }
}
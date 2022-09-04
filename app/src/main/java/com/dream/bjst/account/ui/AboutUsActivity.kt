package com.dream.bjst.account.ui

import android.os.Bundle
import android.view.View
import com.dream.bjst.account.vm.AboutUsViewModel
import com.dream.bjst.databinding.ActivityAboutUsBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class AboutUsActivity : BaseActivity<AboutUsViewModel, ActivityAboutUsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        event()
    }

    private fun event() {
        mBinding.aboutUsTitle.leftView.ktClick() {
            onBackPressed()
        }
    }

    override fun initDataOnResume() {
        TODO("Not yet implemented")
    }
}
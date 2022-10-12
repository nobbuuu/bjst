package com.kredit.cash.loan.app.account.ui

import android.os.Bundle
import com.kredit.cash.loan.app.account.vm.AccountViewModel
import com.kredit.cash.loan.app.databinding.ActivityAboutUsBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class AboutUsActivity : BaseActivity<AccountViewModel, ActivityAboutUsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
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
    }
}
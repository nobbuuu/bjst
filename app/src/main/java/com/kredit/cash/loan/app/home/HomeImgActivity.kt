package com.kredit.cash.loan.app.home

import android.os.Bundle
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.databinding.ActivityHomeImgBinding
import com.kredit.cash.loan.app.main.vm.MainViewModel
import com.kredit.cash.loan.app.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class HomeImgActivity : BaseActivity<MainViewModel, ActivityHomeImgBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.adjustWindow(this, color = R.color.color_F8FFF0, mBinding.rootLay)
        mBinding.homeImgIv.ktClick {
            finish()
        }
    }

    override fun initData() {
    }

    override fun initDataOnResume() {

    }
}
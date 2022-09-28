package com.dream.bjst.home

import android.os.Bundle
import com.dream.bjst.R
import com.dream.bjst.databinding.ActivityHomeImgBinding
import com.dream.bjst.main.vm.MainViewModel
import com.dream.bjst.utils.StatusBarUtils
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
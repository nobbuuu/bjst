package com.dream.bjst.home

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R
import com.dream.bjst.databinding.ActivityHomeBinding
import com.dream.bjst.home.vm.HomeViewModel
import com.dream.bjst.login.LoginActivity
import com.dream.bjst.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.adjustWindow(this,R.color.color_F8FFF0,mBinding.topLay)
        //当前页面是未登录展示，如果已经登录将不展示
      mBinding.homeRequestBtn.ktClick {
            ktStartActivity(LoginActivity::class)
        }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }
}
package com.dream.bjst.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dream.bjst.databinding.ActivityHomeBinding
import com.dream.bjst.home.vm.HomeViewModel
import com.dream.bjst.login.LoginActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

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
package com.dream.bjst.other

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R
import com.dream.bjst.account.ui.PrivacyActivity
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityLaunchBinding
import com.dream.bjst.home.HomeActivity
import com.dream.bjst.identification.ui.*
import com.dream.bjst.login.LoginActivity
import com.dream.bjst.main.MainActivity
import com.dream.bjst.main.vm.MainViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.utils.MmkvUtil

/**
 *@author tiaozi
 *@date   2022/1/20
 *description
 */
class LaunchActivity : BaseActivity<MainViewModel, ActivityLaunchBinding>() {
    var mHandler = Handler(Looper.getMainLooper())
    override fun initStateBar(stateBarColor: Int, isLightMode: Boolean, fakeView: View?) {
        super.initStateBar(ColorUtils.getColor(R.color.transparent), isLightMode, fakeView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this.isTaskRoot) {
            finish()
            overridePendingTransition(0, 0)
            return
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        mHandler.postDelayed(Runnable {
            val isFirst = MmkvUtil.decodeBoolean("isFirst")
            if (isFirst == true) {
                ktStartActivity(PrivacyActivity::class)
            } else {
                if (UserManager.isLogin()) {
                    viewModel.fetchCustomerKycStatus()
                } else {
                    ktStartActivity(LoginActivity::class)
                }
            }
        }, 4000) //3秒

        if (!this.isTaskRoot) { // 判断当前activity是不是所在任务栈的根
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.userStatus.observe(this) {
            if (it.`959898BD809199A4958787`) {
                ktStartActivity(MainActivity::class)
            } else {
                ktStartActivity(HomeActivity::class)
            }
        }
    }

    override fun initDataOnResume() {

    }

}
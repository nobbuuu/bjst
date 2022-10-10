package com.dream.bjst.other

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R
import com.dream.bjst.account.ui.PrivacyActivity
import com.dream.bjst.common.Constant
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityLaunchBinding
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

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.getIpAddress()
        if (!this.isTaskRoot) {
            finish()
            overridePendingTransition(0, 0)
            return
        }
    }

    override fun initData() {
        mHandler.postDelayed(Runnable {
            val isFirst = MmkvUtil.decodeBooleanOpen("isFirst")
            if (isFirst == true) {
                ktStartActivity(PrivacyActivity::class) {
                    putExtra("actionType", 1)
                }
                finish()
            } else {
                if (UserManager.isLogin()) {
                    viewModel.fetchCustomerKycStatus()
                } else {
                    ktStartActivity(MainActivity::class) {
                        putExtra(Constant.actionType, Constant.ACTION_TYPE_HOME)
                    }
                    finish()
                }
            }
        }, 3000) //3秒
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.userStatus.observe(this) {
            mBinding.rootLay.post {
                var action = Constant.ACTION_TYPE_HOME
                if (it.`959898BD809199A4958787`) {
                    action = Constant.ACTION_TYPE_MAIN
                }
                ktStartActivity(MainActivity::class) {
                    putExtra(Constant.actionType, action)
                }
                finish()
            }
        }

        viewModel.unLogin.observe(this) {
            mBinding.rootLay.post {
                ktStartActivity(LoginActivity::class)
            }
        }
    }

    override fun initDataOnResume() {

    }

}
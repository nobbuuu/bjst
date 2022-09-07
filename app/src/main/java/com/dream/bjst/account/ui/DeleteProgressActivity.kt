package com.dream.bjst.account.ui

import android.os.Bundle
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityDeleteProgressBinding
import com.dream.bjst.home.HomeActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktStartActivity

class DeleteProgressActivity : BaseActivity<AccountViewModel, ActivityDeleteProgressBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        Thread {
            for (i: Int in 10..100 step 10) {
                mBinding.deleteProgressBar.setProgress(i)
                Thread.sleep(1000)
                if (i == 100) {
                    runOnUiThread() {
//                        UserManager.clearUserInfo()
                        mBinding.deteTv.text = "All your date has been cleared!"
                        Thread.sleep(3000)
                      ktStartActivity(HomeActivity::class)
                    }
                }
            }
        }.start()
    }

    override fun initData() {


    }

    override fun initDataOnResume() {

    }
}
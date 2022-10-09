package com.dream.bjst.account.ui

import android.os.Bundle
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityDeleteProgressBinding
import com.dream.bjst.login.LoginActivity
import com.dream.bjst.main.MainActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktStartActivity

class DeleteProgressActivity : BaseActivity<AccountViewModel, ActivityDeleteProgressBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        Thread {
            for (i: Int in 10..100 step 20) {
                mBinding.deleteProgressBar.setProgress(i)
                Thread.sleep(1000)
                if (i == 100) {
                    runOnUiThread() {
                        UserManager.clearUserInfo()
                        mBinding.deteTv.text = "All your date has been cleared!"
                        Thread.sleep(2000)
                      ktStartActivity(LoginActivity::class)
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
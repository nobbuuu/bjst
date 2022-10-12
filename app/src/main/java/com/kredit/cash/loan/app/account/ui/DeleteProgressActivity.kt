package com.kredit.cash.loan.app.account.ui

import android.os.Bundle
import com.kredit.cash.loan.app.account.vm.AccountViewModel
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.databinding.ActivityDeleteProgressBinding
import com.kredit.cash.loan.app.login.LoginActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktStartActivity

class DeleteProgressActivity : BaseActivity<AccountViewModel, ActivityDeleteProgressBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        Thread {
            for (i: Int in 0..100 step 20) {
                mBinding.deleteProgressBar.setProgress(i)
                Thread.sleep(1000)
                if (i == 100) {
                    runOnUiThread() {
                        mBinding.deteTv.text = "All your date has been cleared!"
                        UserManager.clearUserInfo()
                        Thread.sleep(3000)
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
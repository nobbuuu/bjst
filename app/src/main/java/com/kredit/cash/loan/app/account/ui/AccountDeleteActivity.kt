package com.kredit.cash.loan.app.account.ui

import android.os.Bundle
import com.kredit.cash.loan.app.account.vm.AccountViewModel
import com.kredit.cash.loan.app.databinding.ActivityAccountDeleteBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class AccountDeleteActivity :BaseActivity<AccountViewModel,ActivityAccountDeleteBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
    }


    override fun startObserve() {
        super.startObserve()
        viewModel.accountDeleteResult.observe(this){
            Thread.sleep(3000)
             if (it) ktStartActivity(com.kredit.cash.loan.app.account.ui.DeleteProgressActivity::class)
        }
    }

    override fun initData() {
        viewModel.accountDeleteData()
        event()
    }

    override fun initDataOnResume() {

    }

    /**
     * 自定义点击方法
     */
    private fun event() {
        mBinding.accountDeleteTitle.leftView.ktClick {
            onBackPressed()
        }

    }
}
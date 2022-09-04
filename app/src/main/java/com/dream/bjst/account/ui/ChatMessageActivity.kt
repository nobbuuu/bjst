package com.dream.bjst.account.ui

import android.os.Bundle
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.databinding.ActivityChatMessageBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class ChatMessageActivity:BaseActivity<AccountViewModel,ActivityChatMessageBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.accountChatTitle.getLeftView().ktClick {
            onBackPressed()
        }

    }



    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
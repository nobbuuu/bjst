package com.dream.bjst.account.ui

import android.os.Bundle
import android.webkit.WebView
import com.dream.bjst.account.vm.ChatMessageModel
import com.dream.bjst.databinding.ActivityChatMessageBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class ChatMessageActivity:BaseActivity<ChatMessageModel,ActivityChatMessageBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.accountChatTitle.getLeftView().ktClick {
            onBackPressed()
        }

    }



    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initDataOnResume() {
        TODO("Not yet implemented")
    }
}
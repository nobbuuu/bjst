package com.dream.bjst.other

import android.os.Bundle
import com.dream.bjst.databinding.ActivityWebBinding
import com.dream.bjst.main.vm.MainViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.nullToEmpty

/**
 *@author tiaozi
 *@date   2022/1/20
 *description
 */
class WebViewActivity : BaseActivity<MainViewModel, ActivityWebBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        val url  = intent.getStringExtra("webUrl")
        mBinding.webView.loadUrl(url.nullToEmpty())
    }

    override fun initDataOnResume() {

    }

}
package com.dream.bjst.repayment.ui

import android.os.Bundle
import android.webkit.WebView

import com.dream.bjst.databinding.ActivityReqPaymentBinding
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class ReqPaymentActivity : BaseActivity<RepaymentViewModel, ActivityReqPaymentBinding>() {
    var repaymentUrl:String?=null
     var webView:WebView?=null
    override fun initView(savedInstanceState: Bundle?) {
      repaymentUrl=intent.getStringExtra("reqUrl")
        event()
    }

    private fun event() {
        mBinding.reqTitleBar.leftView.ktClick { onBackPressed() }
    }

    override fun initData() {
        repaymentUrl?.let { webView?.loadUrl(it) }
    }

    override fun initDataOnResume() {

    }

}
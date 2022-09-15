package com.dream.bjst.repayment.ui

import android.os.Bundle
import android.webkit.WebView

import com.dream.bjst.databinding.ActivityReqPaymentBinding
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseActivity

class ReqPaymentActivity : BaseActivity<RepaymentViewModel, ActivityReqPaymentBinding>() {
    var repaymentUrl:String?=null
    lateinit var webView:WebView
    override fun initView(savedInstanceState: Bundle?) {
      repaymentUrl=intent.getStringExtra("reqUrl")
    }

    override fun initData() {
        repaymentUrl?.let { webView.loadUrl(it) }
    }

    override fun initDataOnResume() {

    }

}
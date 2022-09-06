package com.dream.bjst.account.ui

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.blankj.utilcode.util.GsonUtils
import com.didichuxing.doraemonkit.util.LogUtils
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityChatMessageBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class ChatMessageActivity : BaseActivity<AccountViewModel, ActivityChatMessageBinding>() {
    val url = "https://www.baidu.com/"
    var isError: Boolean = false

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.accountChatTitle.leftView.ktClick {
            onBackPressed()
        }
        initInternetData()
    }

    /**
     * 拼接参数
     */
    private fun initInternetData() {
        viewModel.chatMessage()
    }

    /**
     * 数据回调方法
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.chatMessageResult.observe(this){






         mBinding.messageWeb.loadUrl(url)
        }


    }


    override fun initData() {
        event()
    }

    private fun event() {
        mBinding.reFresh.ktClick {
            mBinding.reFresh.visibility = View.GONE
            mBinding.messageWeb.reload()
        }
        // Get the web view settings instance
        val settings = mBinding.messageWeb.settings
        // Enable java script in web view
        settings.javaScriptEnabled = true
        // Enable and setup web view cache
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)
        settings.setSupportZoom(false)
        // Enable zooming in web view
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        // Enable disable images in web view
        settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings.loadsImagesAutomatically = true

        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true
        }
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        // More optional settings, you can enable it by yourself
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.setGeolocationEnabled(true)
        settings.allowFileAccess = true
        //webview setting
        mBinding.messageWeb.fitsSystemWindows = true
        /* if SDK version is greater of 19 then activate hardware acceleration
        otherwise activate software acceleration  */
        mBinding.messageWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        //装载url
//        mBinding.messageWeb.loadUrl(url)
        // Set web view client
        mBinding.messageWeb.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Page loading started
                // Do something
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                isError = true
                view?.visibility = View.GONE
                mBinding.reFresh.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Page loading finished
                // Enable disable back forward button
                if (!isError) {
                    //回调成功后的相关操作
                    mBinding.reFresh.visibility = View.GONE
                    view?.visibility = View.VISIBLE
                } else {
                    isError = false

                }
            }

        }
    }

    override fun initDataOnResume() {
    }
}
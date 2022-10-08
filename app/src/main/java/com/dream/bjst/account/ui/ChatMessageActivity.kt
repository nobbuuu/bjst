package com.dream.bjst.account.ui

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.*
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.Constant
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityChatMessageBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick


class ChatMessageActivity : BaseActivity<AccountViewModel, ActivityChatMessageBinding>() {
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
        event()
    }

    /**
     * 数据回调方法
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.chatMessageResult.observe(this) {
            if (it.`978187809B999186A79186829D9791A7839D80979C`) {
                var value =
                    "?customerId=${UserManager.getUserNo()}&userName=${UserManager.getUserName()}&userPhone=${UserManager.getUserPhone()}&customerUId=${UserManager.getCustomerUid()}"
                var chatUrl = it.`978187809B999186B7958691B79C9580A495809C`
                mBinding.messageWeb.loadUrl(chatUrl + value)
            }
        }
    }

    override fun initData() {

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
        mBinding.messageWeb.webChromeClient = mWebViewClient

        /*mBinding.messageWeb.webViewClient = object : WebViewClient() {
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

        }*/
    }

    override fun initDataOnResume() {
    }

    private var mUploadMessage: ValueCallback<Uri?>? = null
    private var mUploadCallbackAboveL: ValueCallback<Array<Uri?>>? = null
    private val FILE_CHOOSER_RESULT_CODE = 200

    private val mWebViewClient: WebChromeClient = object : WebChromeClient() {
        // For Android >= 5.0
        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri?>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            mUploadCallbackAboveL = filePathCallback
            goPhotoAlbum()
            return true
        }
    }

    fun goPhotoAlbum() {
        val intent: Intent
        if (Build.VERSION.SDK_INT < 19) {
            intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
        } else {
            intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return
            val result = if (data == null || resultCode != RESULT_OK) null else data.data
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data)
            } else if (mUploadMessage != null) {
                mUploadMessage?.onReceiveValue(result)
                mUploadMessage = null
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || mUploadCallbackAboveL == null) return
        var results: Array<Uri?>? = null
        if (resultCode == RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    results = arrayOfNulls(clipData.itemCount)
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        results[i] = item.uri
                    }
                }
                if (dataString != null) results = arrayOf(Uri.parse(dataString))
            }
        }
        results?.let {
            mUploadCallbackAboveL?.onReceiveValue(it)
            mUploadCallbackAboveL = null
        }
    }
}
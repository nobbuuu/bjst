//package com.dream.bjst.account.ui;
//
//import android.graphics.Bitmap;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.dream.bjst.R;
//import com.dream.bjst.base.BaseActivity;
//import com.dream.bjst.utils.StatusBarUtils;
//import com.hjq.bar.TitleBar;
//
//
//public class ChatMessageActivity extends BaseActivity {
//    TitleBar mTitleBar;
//    WebView mWebView;
//
//    @Override
//    protected int initLayout() {
//        //改变状态栏文字的颜色
//        StatusBarUtils.adjustWindow(this, true);
//        return R.layout.activity_chat_message;
//    }
//
//    @Override
//    protected void initView() {
//        mTitleBar = fvbi(R.id.account_chat_title);
//        mWebView = fvbi(R.id.message_web);
//    }
//
//    @Override
//    protected void initData() {
//        mTitleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        event();
//        getInternetData();
//    }
//
//    private void getInternetData() {
//
//
//
//    }
//
//    private void event() {
//        //设置支持js否则有些网页无法打开
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.setWebViewClient(new MyClient());
//        mWebView.setWebChromeClient(new MyWebChromeClient());
//        //加载网络url
//        mWebView.loadUrl("http://150.158.186.237/page/app/room.html?customerId=10993&userName=User993&userPhone=2364965504&customerUId=10001_ptWDXGtthlc9E1d3s4ih993");
//    }
//
//    private class MyClient extends WebViewClient {
//        //监听到页面发生跳转的情况，默认打开web浏览器
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            //在webView中加载要打开的链接
//            view.loadUrl(request.getUrl().toString());
//            return true;
//        }
//
//        //页面开始加载
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            Log.e(TAG, "onPageStarted: ");
//        }
//
//        //页面加载完成的回调方法
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            Log.e(TAG, "onPageFinished: ");
//
//        }
//    }
//
//    public class MyWebChromeClient extends WebChromeClient {
//        //监听网页进度 newProgress进度值在0-100
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);
//        }
//
//        //设置Activity的标题与 网页的标题一致
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            super.onReceivedTitle(view, title);
//
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //如果用户按的是返回键 并且WebView页面可以返回
//        if (keyCode == event.KEYCODE_BACK && mWebView.canGoBack()) {
//            //让WebView返回
//            mWebView.goBack();
//            return true;
//        }
//        //如果WebView不能返回 则调用默认的onKeyDown方法 退出Activity
//        return super.onKeyDown(keyCode, event);
//    }
//
//}
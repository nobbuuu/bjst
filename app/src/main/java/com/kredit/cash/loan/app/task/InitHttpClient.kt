package com.kredit.cash.loan.app.task

import android.app.Application
import android.webkit.WebView
import com.kredit.cash.loan.app.BuildConfig
import com.kredit.cash.loan.app.common.UserManager
import com.kingswim.mock.HttpMockInterceptor
import com.tcl.base.rxnetword.RxHttpManager
import com.tcl.launcher.task.MainTask
import com.kredit.cash.loan.app.other.security.EncryptTShopInterceptor
import com.kredit.cash.loan.app.utils.RandomUtils
import okhttp3.Interceptor

import rxhttp.RxHttpPlugins

import java.io.File

/**
 * @author : Yzq
 * time : 2020/11/10 14:01
 */
const val storeUuid = "thome"

class InitHttpClient : MainTask() {

    private val userAgent: String by lazy { WebView(mContext).settings.userAgentString }
    override fun run() {
        initRxHttp()
    }

    private fun initRxHttp() {
        when (mContext) {
            is Application -> {

                val interceptors =
                    mutableListOf<Interceptor>(
                        EncryptTShopInterceptor()
                    ).apply {
                        if (BuildConfig.DEBUG) {
                            //添加模拟数据拦截器
                            add(HttpMockInterceptor())
                        }
                    }
                val file = File(mContext.cacheDir, "netCache")
                RxHttpPlugins.init(RxHttpManager.init(mContext as Application, interceptors))
                    .setDebug(BuildConfig.DEBUG, true)
                    .setCache(file, 10 * 1024 * 1024, 60 * 1000)
                    .setOnParamAssembly { param ->
                        param.addHeader(
                            "Cookie",
                            "D4BCB1B5B0B1A6ABB5A4A4ABB5A1A0BCABA0BBBFB1BA=" + UserManager.getAccessToken()
                        )
                            .addHeader("Cookie", "9091829D9791BD90=10022")//设备编号，暂时写死
                            .addHeader("poiuytrggeqwr22fbc", "2")//加密key,2:参数层级
                            .apply {
                                //请求header可以全局传1-4个随机的key-value进来，可以使得每个包的header数量不一样
                                RandomUtils.getRandomParam().forEach {
                                    addHeader(it.key, it.value)
                                }
                            }
                    }
            }
            else -> {
                throw Exception("Context must be Application")
            }
        }
    }
}
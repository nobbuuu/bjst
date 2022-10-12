package com.kredit.cash.loan.app.net

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain


/**
 * @author : tiaozi
 * time : 2020/11/9 16:15
 * 说明： https://github.com/liujingxing/https://github.com/liujingxing/okhttp-RxHttp/wiki/%E9%85%8D%E7%BD%AEBaseUrl/wiki/%E9%85%8D%E7%BD%AEBaseUrl
 *
 * 手动输入域名，此时 url = "https://www.mi.com/service/..."
 * 手动输入域名 > 指定非默认域名 > 使用默认域名。
 * 动态更改 baseUrl，直接在代码上面改  Utl.baseUrl = "...."
 *
 */
object Url {
    @JvmField
    @DefaultDomain //设置为默认域名
    var baseUrl = Configs.getAppBaseUrl()

    @JvmField
    @Domain(name = "TestUrl") //非默认域名，并取别名为BaseUrlBaidu     .setDomainToTestUrlIfAbsent()  //此时指定Baidu域名无效
    var cnsTestUrl = "http://115.238.46.58:20222"
}
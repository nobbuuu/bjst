package com.dream.bjst.net

import android.net.ParseException
import coil.network.HttpException
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tcl.base.app.BaseConstant
import com.tcl.base.event.LogoutTipsBean
import com.tcl.base.kt.ktToastShow
import com.tcl.base.kt.show
import com.tcl.base.rxnetword.ErrorResponse
import org.json.JSONException
import rxhttp.wrapper.exception.HttpStatusCodeException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * @author : tiaozi
 * time : 2021/1/21 13:59
 */
object HandleThrowable {

    /**
     * 统一处理异常方法，有对登录过期做处理
     *
     */
    fun parseThrowable(
        throwable: Throwable,
        errorBlock: ((Throwable) -> Unit)? = null,
        showToast: Boolean = true
    ) {
        if (throwable is HttpStatusCodeException) {
            doLogout(throwable, errorBlock = errorBlock)

        } else {
            if (showToast) {
                when (throwable) {

                    is SocketTimeoutException, is UnknownHostException -> "连接超时".ktToastShow()

                    is SSLException -> "证书出错".ktToastShow()

                    is ConnectException, is HttpException -> "网络错误".ktToastShow()

                    is rxhttp.wrapper.exception.ParseException -> doLogoutParser(throwable)

                    is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> "解析错误".ktToastShow()

                    else -> {
                        throwable.show()
                    }
                }
            }
            errorBlock?.invoke(throwable)
        }
    }


    private fun doLogout(
        throwable: HttpStatusCodeException,
        showToast: Boolean = true,
        errorBlock: ((Throwable) -> Unit)? = null
    ) {
        val result = throwable.result
        try {
            val fromJson = GsonUtils.fromJson(result, ErrorResponse::class.java)
            if (fromJson.code == BaseConstant.LOGOUT_STATUS_CODE.toString()) {
                LiveEventBus.get(BaseConstant.EVENT_LOGOUT)
                    .post(LogoutTipsBean(true, fromJson.msg ?: "00:00"))
            } else {
                errorBlock?.invoke(throwable)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun doLogoutParser(exception: rxhttp.wrapper.exception.ParseException) {
        if (exception.errorCode == "-500") {//code 是-500 的时候，表示token 过时，或者被占线
            LiveEventBus.get(BaseConstant.EVENT_LOGOUT).post(LogoutTipsBean(false, ""))
        }
        exception.show()
    }

}
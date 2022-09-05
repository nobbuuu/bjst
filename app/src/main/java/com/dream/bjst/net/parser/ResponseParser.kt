package com.dream.bjst.net.parser

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.tcl.base.rxnetword.EncryptUtil
import com.tcl.base.rxnetword.exception.BusinessException
import com.tcl.base.rxnetword.exception.MultiDevicesException
import com.tcl.base.rxnetword.exception.NotSaleManException
import com.tcl.base.rxnetword.exception.TokenTimeOutException
import com.tcl.base.rxnetword.parser.BaseEncryptResponse
import com.tcl.base.rxnetword.parser.BaseResponse
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import rxhttp.wrapper.utils.GsonUtil
import rxhttp.wrapper.utils.convert
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type


/**
 * @author : tiaozi
 * time : 2020/11/9 16:52
 */
@Parser(name = "Response", wrappers = [PageList::class, List::class])
open class ResponseParser<T> : AbstractParser<T> {
    /**
     * 此构造方法适用于任意Class对象，但更多用于带泛型的Class对象，如：List<Student>
     *
     * 用法:
     * Java: .asParser(new ResponseParser<List<Student>>(){})
     * Kotlin: .asParser(object : ResponseParser<List<Student>>() {})
     *
     * 注：此构造方法一定要用protected关键字修饰，否则调用此构造方法将拿不到泛型类型
     */
    protected constructor() : super()

    /**
     * 此构造方法仅适用于不带泛型的Class对象，如: Student.class
     *
     * 用法
     * Java: .asParser(new ResponseParser<>(Student.class))   或者  .asResponse(Student.class)
     * Kotlin: .asParser(ResponseParser(Student::class.java)) 或者  .asResponse<Student>()
     */
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        val strType: Type = ParameterizedTypeImpl[String::class.java, mType]
        val originalStr: String = response.convert(strType)
        val encryptResponse = GsonUtils.fromJson(originalStr, BaseEncryptResponse::class.java)
        val result = EncryptUtil.decode(encryptResponse?.poiuytrggeqwr22fbc)
        Log.d("http", "result = $result")
        val  type: Type = ParameterizedTypeImpl[Response::class.java, mType] //获取泛型类型
        val responseData =JsonUtil.getObject<Response<T>>(result, type)

        if (responseData == null) {
            val specialResponse =
                GsonUtil.fromJson<Response<String>>(
                    originalStr,
                    ParameterizedTypeImpl[Response::class.java, String::class.java]
                )
            when {
                specialResponse.isMultiDeviceLogin() -> {
                    throw MultiDevicesException(
                        specialResponse.code,
                        specialResponse.data.toString(),
                        specialResponse.msg ?: ""
                    )
                }
                else ->
                    throw ParseException("-1010", "数据解析失败,请稍后再试", response)
            }
        } else {
            when {
                responseData.isNotSaleManException() -> {
                    throw NotSaleManException(responseData.code, responseData.msg ?: "")
                }
                responseData.isMultiDeviceLogin() -> {
                    throw MultiDevicesException(
                        responseData.code,
                        responseData.data.toString(),
                        response.message
                    )
                }
                responseData.isBusinessException() -> {
                    throw BusinessException(
                        responseData.code,
                        responseData.msg ?: "报错信息为空"
                    )
                }
                responseData.isTokenTimeOut() -> {
                    throw TokenTimeOutException(
                        responseData.code,
                        responseData.msg ?: ""
                    )
                }
                !(responseData.isSuccess()) -> {
                    throw ParseException(responseData.code, responseData.msg, response)
                }
                else -> {
                    var t = responseData.data //获取data字段

                    if (t == null) {
                        if (mType === String::class.java) {
                            /*
                             * 考虑到有些时候服务端会返回：{"code":0,"errorMsg":"关注成功"}  类似没有data的数据
                             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
                             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
                             */
                            @Suppress("UNCHECKED_CAST")
                            t = response.message as T
                            return t
                        } else {
                            throw ParseException(
                                responseData.code,
                                "data 为null，请用String接收",
                                response
                            )
                        }
                    }
                    return t
                }
            }

        }
    }
}

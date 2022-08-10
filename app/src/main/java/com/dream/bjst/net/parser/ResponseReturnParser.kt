package com.dream.bjst.net.parser

import com.tcl.base.rxnetword.exception.BusinessException
import com.tcl.base.rxnetword.exception.MultiDevicesException
import com.tcl.base.rxnetword.exception.NotSaleManException
import com.tcl.base.rxnetword.exception.TokenTimeOutException
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import rxhttp.wrapper.utils.GsonUtil
import rxhttp.wrapper.utils.convert
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType


/**
 * @author : tiaozi
 * time : 2020/11/9 16:52
 */
@Parser(name = "ResponseReturn")
open class ResponseReturnParser<T> : AbstractParser<Response<T>> {
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
    override fun onParse(response: okhttp3.Response): Response<T> {

        val strType: Type = ParameterizedTypeImpl[String::class.java, mType]
        val originalStr: String = response.convert(strType)

        val type: Type = ParameterizedTypeImpl[Response::class.java, mType] //获取泛型类型
        val responseData = JsonUtil.getObject<Response<T>>(originalStr, type)

        if (responseData == null) {
            val specialResponse =
                GsonUtil.fromJson<Response<String>>(originalStr, ParameterizedTypeImpl[Response::class.java, String::class.java])
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
        } else {//获取data字段
            //获取data字段
            when {
                responseData.isNotSaleManException() -> {
                    throw NotSaleManException(responseData.code, responseData.msg ?: "")
                }
                responseData.isMultiDeviceLogin() -> {
                    throw MultiDevicesException(responseData.code,responseData.data.toString(),response.message)
                }
                responseData.isBusinessException() -> {
                    throw BusinessException(responseData.code, responseData.msg ?: "报错信息为空")
                }
                responseData.isTokenTimeOut() -> {
                    throw TokenTimeOutException(responseData.code, responseData.msg ?: "")
                }
                !(responseData.isSuccess()) -> {
                    throw ParseException(responseData.code, responseData.msg, response)
                }
                else -> {
                    val t = responseData.data //获取data字段
                    //data 为Obj || list
                    if(isNoDataType(mType) || isNoDataType(getDataIsListItemType(mType))){
                        return responseData
                    }

                    return if (t == null)
                        if (mType === String::class.java) {
                            responseData
                        } else {
                            throw ParseException(
                                responseData.code,
                                "data 为null，请用String接收",
                                response
                            )
                        } //获取data字段
                    else {
                        responseData
                    }
                }
            }

        }

    }

    private fun getDataIsListItemType(mType:Type): Type? {
        return ((mType as? ParameterizedType)?.actualTypeArguments?.firstOrNull() as? WildcardType)?.upperBounds?.firstOrNull()
    }

    private fun isNoDataType(mType:Type?):Boolean {
        if( mType == null ){
            return false
        }
        return mType is Class<*> && NoDataType::class.java.isAssignableFrom(mType)
    }
}
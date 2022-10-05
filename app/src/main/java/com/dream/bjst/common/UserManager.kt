package com.dream.bjst.common

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.bean.UserInfo
import com.dream.bjst.common.MmkvConstant.KEY_ACCESS_TOKEN
import com.dream.bjst.common.MmkvConstant.KEY_ACCOUNTID
import com.dream.bjst.common.MmkvConstant.KEY_CUSTOMER_EMAIL
import com.dream.bjst.common.MmkvConstant.KEY_CUSTOMER_ID
import com.dream.bjst.common.MmkvConstant.KEY_CUSTOMER_UID
import com.dream.bjst.common.MmkvConstant.KEY_IS_FALSE_ACCOUNT
import com.dream.bjst.common.MmkvConstant.KEY_REFRESH_TOKEN
import com.dream.bjst.common.MmkvConstant.KEY_USERNO
import com.dream.bjst.common.MmkvConstant.KEY_USER_NAME
import com.dream.bjst.common.MmkvConstant.KEY_USER_PHONE
import com.dream.bjst.common.MmkvConstant.KEY_VISIBLE_INVITE
import com.dream.bjst.common.MmkvConstant.KEY_VISIBLE_MYMONEY
import com.dream.bjst.common.MmkvConstant.KEY_VISIBLE_PARTNER
import com.dream.bjst.common.MmkvConstant.KEY_VISIBLE_PROFIT
import com.dream.bjst.common.MmkvConstant.KEY_VISIBLE_WALLET
import com.tcl.base.kt.isNotEmptyOrNullString
import com.tcl.base.utils.MmkvUtil
import rxhttp.wrapper.utils.GsonUtil

/**
 * desc   : 用户信息管理类
 * author : tanksu
 * date   : 2019-11-17
 */
object UserManager {
    var userInfoBean: UserInfo? = null
    private val mCustomerLiveData: MutableLiveData<UserInfo?> = MutableLiveData()

    init {
        val userInfo = MmkvUtil.decryptGet(MmkvConstant.KEY_USER_INFO)
        LogUtils.iTag("tanKsu", "本地储存的用户信息--->${userInfo}")
        if (userInfo?.isNotEmpty() == true) {
            userInfoBean = GsonUtil.fromJson(userInfo, UserInfo::class.java)
            mCustomerLiveData.postValue(userInfoBean)
        } else {
            userInfoBean = null
            mCustomerLiveData.postValue(userInfoBean)
        }
    }

    /**是否登录APP了，取决于accesstoken有没有值*/
    fun isLogin(): Boolean {
        return getAccessToken().isNotEmpty()
    }

    /**更新用户信息*/
    fun updateUserInfo(userInfoStr: String) {
        if (!userInfoStr.isNullOrEmpty()) {
            MmkvUtil.encryptSave(MmkvConstant.KEY_USER_INFO, userInfoStr)
            userInfoBean = GsonUtil.fromJson(userInfoStr, UserInfo::class.java)
        }
    }

    /**获取用户信息*/
    fun getUserInfo(): String? {
        return MmkvUtil.decryptGet(MmkvConstant.KEY_USER_INFO)
    }

    /**获取token*/
    fun getAccessToken(): String {
        return MmkvUtil.decodeString(getAccessTokenKey()) ?: ""
    }

    /**缓存token*/
    fun setAccessToken(token: String) {
        MmkvUtil.encryptSave(getAccessTokenKey(), token)
    }


    /**获取AccountId*/
    fun getAccountId(): String {
        return MmkvUtil.decryptGet(getAccountIdKey()) ?: "null"
    }


    /**缓存customerId客户编号,登录之前的接口可为null*/
    fun setUserNo(value: String) {
        MmkvUtil.encode(getUserNoKey(), value)
    }

    /**获取customerId客户编号,登录之前的接口可为null*/
    fun getUserNo(): String {
        return MmkvUtil.decodeString(getUserNoKey()) ?: "null"
    }

    /**获取用户姓名*/
    fun setUserName(name: String) {
        MmkvUtil.encode(getUserNameKey(), name)
    }

    /**获取用户姓名*/
    fun getUserName(): String {
        return MmkvUtil.decodeString(getUserNameKey()) ?: ""
    }

    /**设置用户电话号码*/
    fun setUserPhone(phone: String) {
        MmkvUtil.encode(getUserPhoneKey(), phone)
    }

    /**设置是否假账号*/
    fun setFalseAccount(isFalse: Boolean) {
        MmkvUtil.encode(getIsFalseAccountKey(), isFalse)
    }

    /**设置是否假账号*/
    fun isFalseAccount(): Boolean {
        return MmkvUtil.decodeBoolean(getIsFalseAccountKey()) ?: false
    }

    /**获取用户电话号码*/
    fun getUserPhone(): String {
        return MmkvUtil.decodeString(getUserPhoneKey()) ?: "null"
    }

    /**设置用户UID*/
    fun setCustomerUid(uid: String) {
        MmkvUtil.encode(getCustomerUidKey(), uid)
    }

    /**获取用户UID*/
    fun getCustomerUid(): String {
        return MmkvUtil.decodeString(getCustomerUidKey()) ?: "null"
    }

    /**设置用户Email*/
    fun customerEmail(email: String) {
        MmkvUtil.encode(getCustomerEmailKey(), email)
    }

    /**设置用户Email*/
    fun getCustomerEmail(): String {
        return MmkvUtil.decodeString(getCustomerEmailKey()) ?: "null"
    }

    /**设置用户贷款id*/
    fun setCustomerLoanId(id: String) {
        MmkvUtil.encode(getCustomerIdKey(), id)
    }

    /**获取用户贷款id*/

    fun getCustomerLoanId(): String {
        return MmkvUtil.decodeString(getCustomerIdKey()) ?: "null"
    }

    /**清空本地数据*/
    fun clearUserInfo() {
        MmkvUtil.removeKey(getAccessTokenKey())
        MmkvUtil.removeKey(getUserNoKey())
        MmkvUtil.removeKey(getUserNameKey())
        MmkvUtil.removeKey(getUserPhoneKey())
        MmkvUtil.removeKey(getCustomerEmailKey())
        MmkvUtil.removeKey(getCustomerUidKey())

        MmkvUtil.removeKey(MmkvConstant.KEY_USER_INFO)
        MmkvUtil.removeKey(getAccountIdKey())
        MmkvUtil.removeKey(KEY_VISIBLE_INVITE)
        MmkvUtil.removeKey(KEY_VISIBLE_MYMONEY)
        MmkvUtil.removeKey(KEY_VISIBLE_PARTNER)
        MmkvUtil.removeKey(KEY_VISIBLE_WALLET)
        MmkvUtil.removeKey(KEY_VISIBLE_PROFIT)
        userInfoBean = null
        mCustomerLiveData.value = userInfoBean
    }

    /**获取唯一的前缀key，可以用来区分多账号*/
    fun getUserUniquePreKey(): String {
        return "rapid_"
    }

    fun getAccessTokenKey() = "${getUserUniquePreKey()}_${KEY_ACCESS_TOKEN}"
    fun getRefreshTokenKey() = "${getUserUniquePreKey()}_${KEY_REFRESH_TOKEN}"
    fun getAccountIdKey() = "${getUserUniquePreKey()}_${KEY_ACCOUNTID}"
    fun getUserNoKey() = "${getUserUniquePreKey()}_${KEY_USERNO}"
    fun getUserNameKey() = "${getUserUniquePreKey()}_${KEY_USER_NAME}"
    fun getUserPhoneKey() = "${getUserUniquePreKey()}_${KEY_USER_PHONE}"
    fun getCustomerUidKey() = "${getUserUniquePreKey()}_${KEY_CUSTOMER_UID}"
    fun getCustomerEmailKey() = "${getUserUniquePreKey()}_${KEY_CUSTOMER_EMAIL}"
    fun getCustomerIdKey() = "${getUserUniquePreKey()}_${KEY_CUSTOMER_ID}"
    fun getIsFalseAccountKey() = "${getUserUniquePreKey()}_${KEY_IS_FALSE_ACCOUNT}"

}
package com.dream.bjst.common

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.bean.UserInfo
import com.dream.bjst.common.MmkvConstant.KEY_ACCESS_TOKEN
import com.dream.bjst.common.MmkvConstant.KEY_ACCOUNTID
import com.dream.bjst.common.MmkvConstant.KEY_REFRESH_TOKEN
import com.dream.bjst.common.MmkvConstant.KEY_USERNO
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

    /**缓存userNo*/
    fun setUserNo(value: String) {
        MmkvUtil.encode(getUserNoKey(), value)
    }

    /**获取AccountId*/
    fun getAccountId(): String {
        return MmkvUtil.decryptGet(getAccountIdKey()) ?: ""
    }

    /**获取userNo*/
    fun getUserNo(): String {
        return MmkvUtil.decodeString(getUserNoKey()) ?: ""
    }

    /**清空本地数据*/
    fun clearUserInfo() {
        MmkvUtil.removeKey(getAccessTokenKey())
        MmkvUtil.removeKey(getUserNoKey())
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

}
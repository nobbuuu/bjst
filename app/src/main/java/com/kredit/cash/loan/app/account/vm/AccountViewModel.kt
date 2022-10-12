package com.kredit.cash.loan.app.account.vm

import com.kredit.cash.loan.app.common.vm.KYCStatusViewModel
import com.kredit.cash.loan.app.net.Api
import com.tcl.base.event.SingleLiveEvent

/**
 *@author tiaozi
 *@date   2022/1/26
 *description
 */
class AccountViewModel : KYCStatusViewModel() {

    val chatMessageResult = SingleLiveEvent<com.kredit.cash.loan.app.account.bean.ChatMessageBean>()
    val accountDeleteResult = SingleLiveEvent<Boolean>()
    val privacyResult = SingleLiveEvent<com.kredit.cash.loan.app.account.bean.PrivacyBean>()

    /**
     * 删除用户数据
     */
    fun accountDeleteData() {
        rxLaunchUI({
            var accountResult = Api.deleteAccountData()
            accountDeleteResult.postValue(accountResult)
        })

    }

    /**
     * 客服聊天
     */
    fun chatMessage() {
        rxLaunchUI({
            var chatMessageRes = Api.chatMessage()
            chatMessageResult.postValue(chatMessageRes)
        })

    }

    /**
     * 隐私协议
     */
    fun privacy() {
        rxLaunchUI({
            var privacyRes = Api.privacyContent()
            privacyResult.postValue(privacyRes)
        })
    }

}
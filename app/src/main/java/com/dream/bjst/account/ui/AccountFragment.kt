package com.dream.bjst.account.ui

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.R
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.FragmentAccountBinding
import com.dream.bjst.identification.ui.ApproveMainActivity
import com.dream.bjst.loan.ui.LoanRecordsActivity
import com.dream.bjst.other.WebViewActivity
import com.dream.bjst.utils.StatusBarUtils.adjustWindow
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktStartActivity4Result
import com.tcl.base.kt.text
import com.tcl.base.utils.MmkvUtil

class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.privacy()
        adjustWindow(requireActivity(), R.color.color_F8FFF0, mBinding.topLay)
        //设置用户姓名
        mBinding.accountName.text=UserManager.getUserName()
        //设置电话号码
        mBinding.accountPhone.text=UserManager.getUserPhone()
    }

    override fun initDataOnViewCreated() {
        super.initDataOnViewCreated()
        //设置页面
        mBinding.accountSetting.ktClick {
            ktStartActivity(AccountSettingActivity::class)
        }
        //关于我们

        mBinding.accountAboutUs.ktClick {
            ktStartActivity(AboutUsActivity::class)
        }

        //删除数据

        mBinding.accountDeleteIndividualData.ktClick {
            ktStartActivity(AccountDeleteActivity::class)
        }

        //隐私界面

        mBinding.accountPrivacyPolice.ktClick {
            //跳转隐私政策
            viewModel.privacyResult.value?.`84869D8295978DB59386919199919A80A18698`?.let {
                ktStartActivity(WebViewActivity::class) {
                    putExtra("webUrl", it)
                }
            }

//            ktStartActivity(PrivacyActivity::class)
        }

        //顾客聊天服务

        mBinding.accountChatService.ktClick {
            ktStartActivity(ChatMessageActivity::class)
        }
        //进入贷款记录界面

        mBinding.accountLoanRecord.ktClick {
            ktStartActivity4Result(LoanRecordsActivity::class,922)
        }
        //进入认证界面

        mBinding.accountReEnterRv.ktClick {
            ktStartActivity(ApproveMainActivity::class)
        }

    }
//
//    override fun startObserve() {
//        super.startObserve()
//        viewModel.privacyResult.observe(this) {
//            mBinding.webView.loadUrl(it.`849186999D87879D9B9AB18C8498959D9AA18698`)//权限说明地址
//            var privacyAgreementBreviaryUrl =
//                it.`84869D8295978DB59386919199919A80B68691829D95868DA18698`//隐私权限（简）地址
//            var privacyAgreementUrl = it.`84869D8295978DB59386919199919A80A18698`//隐私协议地址
//            var registerAgreementUrl = it.`8691939D87809186B59386919199919A80A18698`//注册协议地址
//            LogUtils.dTag("urlTag", "privacyAgreementBreviaryUrl = $privacyAgreementBreviaryUrl")
//            LogUtils.dTag("urlTag", "privacyAgreementUrl = $privacyAgreementUrl")
//            LogUtils.dTag("urlTag", "registerAgreementUrl = $registerAgreementUrl")
//        }
//    }

}
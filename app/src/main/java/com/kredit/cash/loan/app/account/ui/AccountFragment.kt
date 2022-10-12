package com.kredit.cash.loan.app.account.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.account.vm.AccountViewModel
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.databinding.FragmentAccountBinding
import com.kredit.cash.loan.app.identification.ui.ApproveMainActivity
import com.kredit.cash.loan.app.loan.ui.LoanRecordsActivity
import com.kredit.cash.loan.app.login.LoginActivity
import com.kredit.cash.loan.app.other.WebViewActivity
import com.kredit.cash.loan.app.utils.StatusBarUtils.adjustWindow
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.*

class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        adjustWindow(requireActivity(), R.color.color_F8FFF0, mBinding.topLay)
        //设置用户姓名
        mBinding.accountName.text = UserManager.getUserName().ifEmpty { "User name" }
        //设置电话号码
        mBinding.accountPhone.text = UserManager.getUserPhone().ifEmpty { "Login" }
        viewModel.fetchCustomerKycStatus()
        viewModel.chatMessage()
        mBinding.accountPhone.ktClick {
            if (!UserManager.isLogin()){
                ktStartActivity(LoginActivity::class)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.chatMessageResult.observe(this) {
            mBinding.accountCustomerService.isVisible = UserManager.isLogin() && it.`978187809B999186A79186829D9791A7839D80979C`
        }
        viewModel.userStatus.observe(this) {
            //银行卡重绑
            if (it.`9A919190B09B9D9A93BD809199` == "90") {
                mBinding.approveIdCard.text = "Re-enter A/C No."
                mBinding.identifyAuthenticationIv.isVisible =
                    it.`9A919190B09B9D9A93BD809199` == "90"
            }
        }

        viewModel.privacyResult.observe(this) {
            ktStartActivity(WebViewActivity::class) {
                putExtra("webUrl", it.`84869D8295978DB59386919199919A80A18698`)
            }
        }
    }

    override fun initDataOnViewCreated() {
        super.initDataOnViewCreated()
        //设置页面
        mBinding.accountSetting.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity(com.kredit.cash.loan.app.account.ui.AccountSettingActivity::class)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }
        //关于我们

        mBinding.accountAboutUs.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity(com.kredit.cash.loan.app.account.ui.AboutUsActivity::class)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }

        //删除数据

        mBinding.accountDeleteIndividualData.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity(com.kredit.cash.loan.app.account.ui.AccountDeleteActivity::class)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }

        //隐私界面

        mBinding.accountPrivacyPolice.ktClick {
            if (UserManager.isLogin()) {
                viewModel.privacy()
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }

        //顾客聊天服务

        mBinding.accountCustomerService.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity(com.kredit.cash.loan.app.account.ui.ChatMessageActivity::class)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }
        //进入贷款记录界面

        mBinding.accountLoanRecord.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity4Result(LoanRecordsActivity::class, 922)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }
        //进入认证界面

        mBinding.accountReEnterRv.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity(ApproveMainActivity::class)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }
    }
}
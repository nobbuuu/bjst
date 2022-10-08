package com.dream.bjst.account.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.dream.bjst.R
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.FragmentAccountBinding
import com.dream.bjst.identification.ui.ApproveMainActivity
import com.dream.bjst.loan.ui.LoanRecordsActivity
import com.dream.bjst.other.WebViewActivity
import com.dream.bjst.utils.StatusBarUtils.adjustWindow
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.*

class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.privacy()
        adjustWindow(requireActivity(), R.color.color_F8FFF0, mBinding.topLay)
        //设置用户姓名
        mBinding.accountName.text = UserManager.getUserName()
        //设置电话号码
        mBinding.accountPhone.text = UserManager.getUserPhone()
        viewModel.fetchCustomerKycStatus()
    }

    override fun startObserve() {
        super.startObserve()
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
            viewModel.privacy()
        }

        //顾客聊天服务

        mBinding.accountCustomerService.ktClick {
            ktStartActivity(ChatMessageActivity::class)
        }
        //进入贷款记录界面

        mBinding.accountLoanRecord.ktClick {
            ktStartActivity4Result(LoanRecordsActivity::class, 922)
        }
        //进入认证界面

        mBinding.accountReEnterRv.ktClick {
            ktStartActivity(ApproveMainActivity::class)
        }

    }


}
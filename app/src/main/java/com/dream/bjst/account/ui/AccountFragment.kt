package com.dream.bjst.account.ui

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.dream.bjst.R
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.databinding.FragmentAccountBinding
import com.dream.bjst.identification.ui.ApproveMainActivity
import com.dream.bjst.loan.ui.LoanRecordsActivity
import com.dream.bjst.utils.StatusBarUtils.adjustWindow
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        adjustWindow(requireActivity(), R.color.color_F8FFF0, mBinding.topLay)
    }

    override fun initDataOnViewCreated() {
        super.initDataOnViewCreated()
        //设置页面
//        mBinding.accountSetting.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    AccountSettingActivity::class.java
//                )
//            )
//        }
        mBinding.accountSetting.ktClick {
            ktStartActivity(AccountSettingActivity::class)
        }
        //关于我们

//        mBinding.accountAboutUs.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    AboutUsActivity::class.java
//                )
//            )
//        }
        mBinding.accountAboutUs.ktClick {
            ktStartActivity(AboutUsActivity::class)
        }

        //删除数据
//        mBinding.accountDeleteIndividualData.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    AccountDeleteActivity::class.java
//                )
//            )
//        }
        mBinding.accountDeleteIndividualData.ktClick {
            ktStartActivity(AccountDeleteActivity::class)
        }

        //隐私界面
//        mBinding.accountPrivacyPolice.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    PrivacyActivity::class.java
//                )
//            )
//        }
        mBinding.accountPrivacyPolice.ktClick {
            ktStartActivity(PrivacyActivity::class)
        }

        //顾客聊天服务
//        mBinding.accountChatService.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    ChatMessageActivity::class.java
//                )
//            )
//        }
        mBinding.accountChatService.ktClick {
            ktStartActivity(ChatMessageActivity::class)
        }
        //进入贷款记录界面
//        mBinding.accountLoanRecord.setOnClickListener {
//            startActivity(
//                Intent(
//                    activity,
//                    LoanRecordsActivity::class.java
//                )
//            )
//        }
        mBinding.accountLoanRecord.ktClick {
            ktStartActivity(LoanRecordsActivity::class)
        }
        //进入认证界面
//        mBinding.accountReEnterRv.setOnClickListener {
//            startActivity(Intent(activity, ApproveMainActivity::class.java))
//        }

        mBinding.accountReEnterRv.ktClick {
            ktStartActivity(ApproveMainActivity::class)
        }

    }

}
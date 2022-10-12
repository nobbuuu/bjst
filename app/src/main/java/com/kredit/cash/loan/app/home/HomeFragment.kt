package com.kredit.cash.loan.app.home

import android.os.Bundle
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.databinding.FragmentHomeBinding
import com.kredit.cash.loan.app.home.vm.HomeViewModel
import com.kredit.cash.loan.app.identification.ui.*
import com.kredit.cash.loan.app.login.LoginActivity
import com.kredit.cash.loan.app.main.MainActivity
import com.kredit.cash.loan.app.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.deleteUnUseZero
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.adjustWindow(requireActivity(), R.color.color_F8FFF0, mBinding.topLay)
        mBinding.homeRequestBtn.ktClick {
            if (UserManager.isLogin()) {
                viewModel.userStatus.value?.let {
                    //当前进行到的项目类型,null表示都通过，10：身份证前ocr（跳转到证件ocr界面）,11:身份证后ocr（跳转到证件ocr界面）,20:身份证前ocr（跳转到证件ocr界面）,30:pan卡ocr（跳转到证件ocr界面）,31:pan号认证（跳转到证件ocr界面）,40:活体检测,50:人脸对比,51:人脸对比（跳转到活体）,60:紧急联系人认证,70:扩展信息录入,80:银行卡绑定，90：银行卡重绑定
                    when (it.`9A919190B09B9D9A93BD809199`) {
                        "10", "11", "20", "30", "31" -> {
                            ktStartActivity(ApproveIdCardActivity::class)
                        }
                        "40", "50", "51" -> {
                            ktStartActivity(LivenessDetectionActivity::class)
                        }
                        "60" -> {
                            ktStartActivity(ApproveContactsActivity::class)
                        }
                        "70" -> {
                            ktStartActivity(ApproveIdCardConfirmActivity::class)
                        }
                        "80", "90" -> {
                            ktStartActivity(ApproveBankCardActivity::class)
                        }
                        else -> {
                            ktStartActivity(MainActivity::class)
                        }
                    }
                }
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }
        if (UserManager.isLogin()) {
            viewModel.fetchHomeInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCustomerKycStatus()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.homeData.observe(this) {
            mBinding.amountNum.text = "₹ " + it.`989B959AB5999B819A80`.deleteUnUseZero()
            when (it.`8491869B9D90A19A9D80`) {
                10 -> {//天
                    mBinding.period.text = it.`8491869B9D90`.toString() + " Days"
                }
                20 -> {//月
                    mBinding.period.text = it.`8491869B9D90`.toString() + " months"
                }
            }
            mBinding.amountReceive.text = "₹ " + it.`869197919D8291B5999B819A80`.deleteUnUseZero()
            mBinding.homeRupee.text = "₹ " + it.`879186829D9791B5999B819A80`.deleteUnUseZero()
            mBinding.homePercentValue.text = it.`8D919586A6958091` + "%p.a."
            mBinding.pm.text = "Total Interest@" + it.`999B9A809C87A6958091` + "%p.m."

        }
    }

}
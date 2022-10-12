package com.kredit.cash.loan.app.identification.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.GsonUtils
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.databinding.ActivityBankCardApproveBinding
import com.kredit.cash.loan.app.identification.bean.BankListBean
import com.kredit.cash.loan.app.identification.bean.BindBankCardParam
import com.kredit.cash.loan.app.identification.vm.IdentificationViewModel
import com.kredit.cash.loan.app.main.MainActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktStartActivity4Result
import com.tcl.base.kt.ktToastShow

class ApproveBankCardActivity :
    BaseActivity<IdentificationViewModel, ActivityBankCardApproveBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        viewModel.fetchCustomerKycStatus()
        mBinding.bankLay.ktClick {
            ktStartActivity4Result(ApproveBankListActivity::class, 960)
        }

        mBinding.sureBtn.ktClick {
            val bankName = mBinding.bankLay.getEndText()
            val beneName = mBinding.beneName.getEndEdtText()
            val ifscCode = mBinding.ifscCode.getEndEdtText()
            val accountNo = mBinding.accountNo.getEndEdtText()
            val reAccNo = mBinding.reAccNo.getEndEdtText()
            if (bankName.isEmpty() || beneName.isEmpty() || ifscCode.isEmpty() || accountNo.isEmpty() || reAccNo.isEmpty()) {
                "Please enter the complete information".ktToastShow()
            } else {
                if (accountNo == reAccNo) {
                    viewModel.customerBindBankCard(
                        GsonUtils.toJson(
                            BindBankCardParam(
                                `96959A9FBA959991` = bankName,
                                `9D928797` = ifscCode,
                                `96959A9FA1879186BA959991` = beneName,
                                `8295989D90958091A08D8491` = if (viewModel.idCardStatus.value?.`9A919190B09B9D9A93BD809199` == "90") "20" else "10",
                                `96959A9FB597979B819A80` = accountNo,
                            )
                        )
                    )
                } else {
                    mBinding.tipsTv.isVisible = true
                    mBinding.accountNo.setLineColor(R.color.color_E80000)
                    mBinding.reAccNo.setLineColor(R.color.color_E80000)
                }
            }
        }
        mBinding.accountNo.mEndEdt?.addTextChangedListener {
            mBinding.tipsTv.isVisible = false
            mBinding.accountNo.setLineColor(R.color._xpopup_list_divider)
            mBinding.reAccNo.setLineColor(R.color._xpopup_list_divider)
        }
        mBinding.reAccNo.mEndEdt?.addTextChangedListener {
            mBinding.tipsTv.isVisible = false
            mBinding.accountNo.setLineColor(R.color._xpopup_list_divider)
            mBinding.reAccNo.setLineColor(R.color._xpopup_list_divider)
        }

    }

    override fun initData() {
        viewModel.updateDeviceInfo()
        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }
    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.bindBank.observe(this) {
            if (it.`869187819880`) {
                ktStartActivity(MainActivity::class) {
                    putExtra(Constant.actionType, Constant.ACTION_TYPE_MAIN)
                }
                finish()
            } else {
                getString(R.string.try_again).ktToastShow()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 960 && resultCode == 999) {
            val bank = data?.getSerializableExtra("bank") as BankListBean
            bank?.let {
                mBinding.bankLay.setEndTextColor(R.color.black)
                mBinding.bankLay.setEndText(it.`96959A9FBA959991`)
            }
        }
    }

    override fun onBackPressed() {
        ktStartActivity(MainActivity::class)
        super.onBackPressed()
    }
}
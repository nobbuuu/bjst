package com.dream.bjst.identification.ui

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.R
import com.dream.bjst.databinding.ActivityBankCardApproveBinding
import com.dream.bjst.databinding.ActivityContactsApproveBinding
import com.dream.bjst.identification.bean.BankListBean
import com.dream.bjst.identification.bean.BindBankCardParam
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.main.MainActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktStartActivity4Result
import com.tcl.base.kt.ktToastShow

class ApproveBankCardActivity :
    BaseActivity<IdentificationViewModel, ActivityBankCardApproveBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
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
                                `8295989D90958091A08D8491` = "10",
                                `96959A9FB597979B819A80` = accountNo,
                            )
                        )
                    )
                } else {
                    "The two entered accounts are different".ktToastShow()
                }
            }
        }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.bindBank.observe(this) {
            if (it.`869187819880`) {
                ktStartActivity(MainActivity::class)
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

}
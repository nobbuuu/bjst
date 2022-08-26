package com.dream.bjst.loan.ui

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.databinding.ActivityLoanRecordsBinding
import com.dream.bjst.loan.adapter.LoanRecordsAdapter
import com.tcl.base.common.BaseViewModel
import com.tcl.base.common.ui.BaseActivity

class LoanRecordsActivity:BaseActivity<BaseViewModel,ActivityLoanRecordsBinding>() {

    val mLoanRecordsAdapter = LoanRecordsAdapter()
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.loanRecords.apply {
            adapter = mLoanRecordsAdapter
        }
    }

    override fun initData() {
        val data = arrayListOf<EmptyBean>()
        repeat(10){
            data.add(EmptyBean())
        }
        mLoanRecordsAdapter.setList(data)
    }

    override fun initDataOnResume() {

    }
}
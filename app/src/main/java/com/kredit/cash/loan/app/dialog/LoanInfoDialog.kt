package com.kredit.cash.loan.app.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.kredit.cash.loan.app.databinding.DialogLoanInfomationBinding
import com.kredit.cash.loan.app.loan.adapter.LoanProductAdapter
import com.kredit.cash.loan.app.loan.bean.LoanConfirmBean
import com.tcl.base.kt.ktClick

/**
 * 贷款申请成功弹框
 */
class LoanInfoDialog(
    context: Context,
    val data: LoanConfirmBean? = null,
    val block: (() -> Unit)? = null
) :
    BaseBindingDialog<DialogLoanInfomationBinding>(context, gravity = Gravity.CENTER) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data?.let {
            mBinding.loanAmountLay.setEndText(it.loanAmount)
            mBinding.repayAmountLay.setEndText(it.repayAmount)
            mBinding.dueDateLay.setEndText(it.dueDate)
            val mAdapter = LoanProductAdapter(true)
            mBinding.loanProductsRv.adapter = mAdapter
            mAdapter.setList(it.products)
        }
        mBinding.sureBtn.ktClick {

            block?.invoke()
        }
        mBinding.closeIv.ktClick {
            dismiss()
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}
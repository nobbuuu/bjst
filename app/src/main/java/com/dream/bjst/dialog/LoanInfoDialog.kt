package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.databinding.DialogAmountBinding
import com.dream.bjst.databinding.DialogLoanInfomationBinding
import com.dream.bjst.loan.adapter.AmountPeriodAdapter
import com.dream.bjst.loan.adapter.LoanProductAdapter
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.dream.bjst.loan.bean.LoanConfirmBean
import com.tcl.base.kt.ktClick
import com.tcl.base.weiget.recylerview.RecycleViewDivider

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
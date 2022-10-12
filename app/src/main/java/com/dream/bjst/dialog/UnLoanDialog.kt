package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.R
import com.dream.bjst.databinding.DialogAmountBinding
import com.dream.bjst.databinding.DialogLoanInfomationBinding
import com.dream.bjst.databinding.DialogUnableLoanBinding
import com.dream.bjst.loan.adapter.AmountPeriodAdapter
import com.dream.bjst.loan.adapter.LoanProductAdapter
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.dream.bjst.loan.bean.LoanConfirmBean
import com.dream.bjst.utils.VerifyCodeTimeDownUtil
import com.tcl.base.kt.ktClick
import com.tcl.base.weiget.recylerview.RecycleViewDivider

/**
 * 贷款申请被拒
 */


class UnLoanDialog(
    context: Context,
    val type: Int = 1,
    val block: (() -> Unit)? = null
) :
    BaseBindingDialog<DialogUnableLoanBinding>(context, gravity = Gravity.CENTER) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val timeAgent = VerifyCodeTimeDownUtil(3000,1000,mBinding.countDownTv)
        if (type == 1) {
            mBinding.tipIv.setImageResource(R.mipmap.ic_need_repay)
            mBinding.title.text = "No credit amount available"
            mBinding.contentTV.text = "repay on time to increase your credit limit"
            mBinding.sureBtn.text = "Make a repayment"
        } else if (type == 2) {
            timeAgent.startNow()
            mBinding.tipIv.setImageResource(R.mipmap.ic_reject)
            mBinding.title.text = "We regret to inform you that your application was rejected"
            mBinding.sureBtn.text = "Delete individual data and log out"

        }
        mBinding.contentTV.isVisible = type == 1
        mBinding.countDownTv.isVisible = type == 2

        mBinding.closeIv.isVisible = type == 1
        mBinding.sureBtn.ktClick {
            dismiss()
            block?.invoke()
        }
        mBinding.closeIv.ktClick {
            dismiss()
        }
        setOnDismissListener {
            timeAgent.cancel()
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}
package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.databinding.DialogAmountBinding
import com.dream.bjst.databinding.DialogLoanInfomationBinding
import com.dream.bjst.loan.adapter.AmountPeriodAdapter
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.tcl.base.weiget.recylerview.RecycleViewDivider

/**
 * 贷款确认弹框
 */
class LoanInfoDialog(context: Context, val list: List<AmountPeriodBean>? = null) :
    BaseBindingDialog<DialogLoanInfomationBinding>(context, gravity = Gravity.CENTER) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
}
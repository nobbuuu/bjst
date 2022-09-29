package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.databinding.DialogAmountBinding
import com.dream.bjst.loan.adapter.AmountPeriodAdapter
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.tcl.base.weiget.recylerview.RecycleViewDivider
/**
 * Amount
 *  比如有6个产品，他们的额度分别是1-6元，3个可以选，3个不能选；
 *  那么下拉金额就是：
 *  可选：1,1+2,1+2+3
 *  不可选：1+2+3+4,1+2+3+4+5 ,1+2+3+4+5+6
 *
 *  Period
 *  分期弹窗
 */
class AmountPeriodDialog(context: Context, val list: List<AmountPeriodBean>? = null, val type: Int = 1) :
    BaseBindingDialog<DialogAmountBinding>(context, gravity = Gravity.CENTER) {
    var listener: ((AmountPeriodBean) -> Unit)? = null
    val mAdapter = AmountPeriodAdapter(type = type) {
        dismiss()
        listener?.invoke(it)
    }
    val divider = RecycleViewDivider(context, LinearLayoutManager.VERTICAL)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (type == 2){
            mBinding.title.text = "Period"
        }
        mBinding.dialogRv.apply {
            adapter = mAdapter
            removeItemDecoration(divider)
            addItemDecoration(divider)
        }
        if (!list.isNullOrEmpty()) {
            mAdapter.setList(list)
        }
    }

    fun setData(data: List<AmountPeriodBean>): AmountPeriodDialog {
        mAdapter.setList(data)
        return this
    }

    fun setOnSelectListener(block: ((AmountPeriodBean) -> Unit)? = null) {
        listener = block
    }
}
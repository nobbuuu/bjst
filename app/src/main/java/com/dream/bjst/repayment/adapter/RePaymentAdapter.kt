package com.dream.bjst.repayment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R

import com.dream.bjst.repayment.bean.RepaymentBean

class RePaymentAdapter : BaseQuickAdapter<RepaymentBean, BaseViewHolder>(R.layout.item_repayment_out) {
    override fun convert(holder: BaseViewHolder, item: RepaymentBean) {
        val inRv = holder.getView<RecyclerView>(R.id.repayment_in_rv)
        val inAdapter = RePaymentInAdapter()
        inRv.adapter = inAdapter
//        inAdapter.setList(item.`908191A09B90958DBB8690918687`.[0])
    }
}
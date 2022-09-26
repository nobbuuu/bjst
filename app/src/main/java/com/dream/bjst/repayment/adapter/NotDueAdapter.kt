package com.dream.bjst.repayment.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.repayment.bean.NotDueOrder
import com.dream.bjst.repayment.bean.RepaymentBean
import com.tcl.base.kt.ktSetImage

/**
 * 创建日期：2022-09-11 on 9:47
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class NotDueAdapter : BaseQuickAdapter<NotDueOrder, BaseViewHolder>(R.layout.item_repayment_in) {
    init {
        addChildClickViewIds(R.id.settleNowBtn)
    }
    override fun convert(holder: BaseViewHolder, item: NotDueOrder) {
        holder.setText(R.id.repayment_loan_days, item.`908191B0958D`.toString()+" days")
        holder.setText(R.id.repayment_repay_amount, "₹ " +item.`869199959D9AA09B809598B5999B819A80`.toString())
        holder.setText(R.id.repayment_loan_name, item.`84869B90819780BA959991`)
        holder.ktSetImage(R.id.repayment_item_icon, item.`9D979BA18698`)
        holder.setGone(R.id.divider,holder.adapterPosition == data.size-1)
    }
}
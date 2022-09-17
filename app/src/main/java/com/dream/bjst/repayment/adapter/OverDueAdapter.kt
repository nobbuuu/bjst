package com.dream.bjst.repayment.adapter

import android.util.Log
import android.widget.Button
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.repayment.bean.OverdueOrder
import com.dream.bjst.repayment.bean.RepaymentBean
import com.dream.bjst.repayment.ui.RepaymentDetailActivity
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.ruffian.library.widget.RTextView
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktSetImage
import com.tcl.base.kt.ktStartActivity

/**
 * 创建日期：2022-09-11 on 9:41
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class OverDueAdapter : BaseQuickAdapter<OverdueOrder, BaseViewHolder>(R.layout.item_repayment_in) {

    init {
        addChildClickViewIds(R.id.settleNowBtn)
    }
    override fun convert(holder: BaseViewHolder, item: OverdueOrder) {

        holder.setText(R.id.repayment_loan_days,item.`908191B0958D`.toString()+"days")
        holder.setText(R.id.repayment_loan_name,item.`84869B90819780BA959991`)
        holder.setText(R.id.repayment_repay_amount,"₹ " +item.`869199959D9AA09B809598B5999B819A80`.toString())
        holder.ktSetImage(R.id.repayment_item_icon, item.`9D979BA18698`)
        holder.setGone(R.id.divider,holder.adapterPosition == data.size-1)

    }


}
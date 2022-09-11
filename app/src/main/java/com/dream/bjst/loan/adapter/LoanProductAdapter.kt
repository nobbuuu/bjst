package com.dream.bjst.loan.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.loan.bean.ProductListBean
import com.ruffian.library.widget.RCheckBox
import com.tcl.base.kt.ktSetImage

class LoanProductAdapter :
    BaseQuickAdapter<ProductListBean, BaseViewHolder>(R.layout.item_other_loan) {
    override fun convert(holder: BaseViewHolder, item: ProductListBean) {
        holder.ktSetImage(R.id.loanImg, item.`9D979BA18698`)
        holder.setText(R.id.nameTv, item.`84869B90819780BA959991`)
        holder.setText(R.id.loanAmount, "₹ " + item.`989B959AB5999B819A80`)
        if (item.isCheck) {
            holder.setImageResource(R.id.loanSelectIv, R.mipmap.cb_check)
        } else {
            holder.setImageResource(R.id.loanSelectIv, R.mipmap.cb_uncheck)
        }
    }
}
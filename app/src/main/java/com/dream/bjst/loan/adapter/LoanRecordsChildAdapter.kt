package com.dream.bjst.loan.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.loan.bean.HistoryBean
import com.dream.bjst.widget.LrKvTextview
import com.tcl.base.kt.ktSetImage

class LoanRecordsChildAdapter :
    BaseQuickAdapter<HistoryBean, BaseViewHolder>(R.layout.item_loan_records_child) {
    override fun convert(holder: BaseViewHolder, item: HistoryBean) {
        holder.ktSetImage(R.id.platformIcon, item.`84869B90819780BD979B`)
        holder.getView<LrKvTextview>(R.id.nameTv).setContent(item.`84869B90819780BA959991`)
        holder.getView<LrKvTextview>(R.id.lrOrderNo).setContent(item.`9B86909186BA8199969186`)
        holder.getView<LrKvTextview>(R.id.amount).setContent(item.`989B959AB5999B819A80`.toString())
        holder.getView<LrKvTextview>(R.id.exTime).setContent(item.`918C849D8695809D9B9AA09D9991`)
        //订单状态,10:审核中，20：审核拒绝，30：待还款，40:已逾期，50：已完成，60：其他，70：订单关闭
        when (item.`9B86909186A78095808187`) {
            10 -> {
                holder.setText(R.id.loanStatus, "Under approval")
            }
            20 -> {
                holder.setText(R.id.loanStatus, "Audit Reject")
            }
            30 -> {
                holder.setText(R.id.loanStatus, "To Be Repaid")
            }
            40 -> {
                holder.setText(R.id.loanStatus, "Overdued")
            }
            50 -> {
                holder.setText(R.id.loanStatus, "Completed")
            }
            60 -> {
                holder.setText(R.id.loanStatus, "Other")
            }
            70 -> {
                holder.setText(R.id.loanStatus, "Closed")
            }
        }
    }
}
package com.kredit.cash.loan.app.loan.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.loan.bean.HistoryBean
import com.kredit.cash.loan.app.widget.LrKvTextview
import com.tcl.base.kt.ktSetImage

class LoanRecordsChildAdapter :
    BaseQuickAdapter<HistoryBean, BaseViewHolder>(R.layout.item_loan_records_child) {
    init {
        addChildClickViewIds(R.id.actionTv)
    }
    override fun convert(holder: BaseViewHolder, item: HistoryBean) {
        holder.ktSetImage(R.id.platformIcon, item.`84869B90819780BD979B`)
        holder.setText(R.id.nameTv, item.`84869B90819780BA959991`)
        holder.getView<LrKvTextview>(R.id.lrOrderNo).setContent(item.`9B86909186BA8199969186`)
        val amountView = holder.getView<LrKvTextview>(R.id.amount)
        amountView.setContent("₹ "+item.`989B959AB5999B819A80`.toString())
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
                holder.setGone(R.id.actionTv,false)
                holder.setText(R.id.actionTv, "Repay now")
            }
            40 -> {
                holder.setText(R.id.loanStatus, "Overdued")
                holder.setGone(R.id.actionTv,false)
                holder.setText(R.id.actionTv, "apply")
                amountView.setTitle("Loan Amount")
            }
            50 -> {
                holder.setText(R.id.loanStatus, "Completed")
                holder.setGone(R.id.actionTv,false)
                holder.setText(R.id.actionTv, "apply")
                amountView.setTitle("Loan Amount：")
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
package com.dream.bjst.loan.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.loan.bean.HistoryBean
import com.dream.bjst.loan.bean.OrderResultBean

class LoanRecordsAdapter :
    BaseQuickAdapter<OrderResultBean, BaseViewHolder>(R.layout.item_loan_records) {
    override fun convert(holder: BaseViewHolder, item: OrderResultBean) {
        val recordsRv = holder.getView<RecyclerView>(R.id.loanRecordsRv)
        val adapter = LoanRecordsChildAdapter()
        recordsRv.adapter = adapter
        adapter.setList(item.`9B86909186B89D8780`)
    }
}
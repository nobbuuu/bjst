package com.dream.bjst.loan.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.loan.bean.HistoryBean
import com.dream.bjst.loan.bean.OrderResultBean

class LoanRecordsAdapter(val block: ((Int) -> Unit)? = null) :
    BaseQuickAdapter<OrderResultBean, BaseViewHolder>(R.layout.item_loan_records) {
    override fun convert(holder: BaseViewHolder, item: OrderResultBean) {
        val recordsRv = holder.getView<RecyclerView>(R.id.loanRecordsRv)
        val adapter = LoanRecordsChildAdapter()
        adapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.actionTv) {
                block?.invoke(
                    item.`9B86909186B89D8780`?.getOrNull(position)?.`9B86909186A78095808187` ?: -1
                )
            }
        }
        recordsRv.adapter = adapter

        adapter.setList(item.`9B86909186B89D8780`)
    }
}
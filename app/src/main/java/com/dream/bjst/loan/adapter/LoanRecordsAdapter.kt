package com.dream.bjst.loan.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean

class LoanRecordsAdapter : BaseQuickAdapter<EmptyBean,BaseViewHolder>(R.layout.item_loan_records) {
    override fun convert(holder: BaseViewHolder, item: EmptyBean) {

        val recordsRv = holder.getView<RecyclerView>(R.id.loanRecordsRv)
        val data = arrayListOf<EmptyBean>()
        repeat(2){
            data.add(EmptyBean())
        }
        val adapter = LoanRecordsChildAdapter()
        recordsRv.adapter = adapter
        adapter.setList(data)
    }
}
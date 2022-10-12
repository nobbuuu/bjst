package com.kredit.cash.loan.app.identification.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.identification.bean.BankListBean

class BankListAdapter :
    BaseQuickAdapter<BankListBean, BaseViewHolder>(R.layout.item_bank) {
    override fun convert(holder: BaseViewHolder, item: BankListBean) {
        holder.setText(R.id.nameTv,item.`96959A9FBA959991`)
    }
}
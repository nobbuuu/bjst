package com.dream.bjst.identification.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.identification.bean.BankListBean

class BankListAdapter :
    BaseQuickAdapter<BankListBean, BaseViewHolder>(R.layout.item_bank) {
    override fun convert(holder: BaseViewHolder, item: BankListBean) {
        holder.setText(R.id.nameTv,item.`96959A9FBA959991`)
    }
}
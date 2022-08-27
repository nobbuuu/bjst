package com.dream.bjst.identification.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.identification.adapter.IdentifyAdapter.Viewholder

class IdentifyAdapter :BaseQuickAdapter<EmptyBean, BaseViewHolder>(R.layout.item_identification_recyclerview) {

    class Viewholder(itemView: View) : BaseViewHolder(itemView) {

    }



    override fun convert(holder: BaseViewHolder, item: EmptyBean) {
        TODO("Not yet implemented")
    }
}
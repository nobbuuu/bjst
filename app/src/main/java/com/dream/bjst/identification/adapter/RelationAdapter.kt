package com.dream.bjst.identification.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.identification.bean.RelationBean
import com.tcl.base.kt.ktClick

class RelationAdapter(val block: ((String) -> Unit)? = null) :
    BaseQuickAdapter<RelationBean, BaseViewHolder>(R.layout.item_relation) {
    override fun convert(holder: BaseViewHolder, item: RelationBean) {
        holder.setText(R.id.startTv, item.relation)
        val endIv = holder.getView<ImageView>(R.id.endIv)
        if (item.isCheck) {
            endIv.setImageResource(R.mipmap.select_check)
        } else {
            endIv.setImageResource(R.mipmap.select_uncheck)
        }
        endIv.ktClick {
            data.forEach {
                it.isCheck = it == item
            }
            notifyDataSetChanged()
            endIv.post {
                block?.invoke(item.relation)
            }
        }
    }
}
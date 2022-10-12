package com.kredit.cash.loan.app.identification.adapter

import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.identification.bean.IdentifyBean
import com.ruffian.library.widget.RImageView
import com.ruffian.library.widget.RTextView

class IdentifyAdapter :
    BaseQuickAdapter<IdentifyBean, BaseViewHolder>(R.layout.item_identification_recyclerview) {
    override fun convert(holder: BaseViewHolder, item: IdentifyBean) {
        if (item.isApproved){
            holder.getView<RelativeLayout>(R.id.item_authentication_rv)
                .setBackgroundResource(R.color.color_identify_select)
            holder.getView<RImageView>(R.id.identify_authentication_arrow)
                .setImageResource(R.mipmap.itentify_select_arrow)
        }else{
            holder.getView<RelativeLayout>(R.id.item_authentication_rv)
                .setBackgroundResource(R.color.color_identify_unselect)
            holder.getView<RImageView>(R.id.identify_authentication_arrow)
                .setImageResource(R.mipmap.itentify_unselect_arrow)
        }
        holder.getView<RImageView>(R.id.identify_authentication).setBackgroundResource(item.icon)
        holder.getView<RTextView>(R.id.identify_authentication_tv).text = item.name
    }
}
package com.dream.bjst.identification.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.identification.bean.identifyBean

class IdentifyAdapter :BaseQuickAdapter<identifyBean, BaseViewHolder>(R.layout.item_identification_recyclerview) {


    override fun convert(holder: BaseViewHolder, item: identifyBean) {

    }
}
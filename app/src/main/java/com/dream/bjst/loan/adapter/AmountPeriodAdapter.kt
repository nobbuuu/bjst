package com.dream.bjst.loan.adapter

import android.widget.ImageView
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.tcl.base.kt.ktClick

class AmountPeriodAdapter(val type: Int = 1, val block: ((AmountPeriodBean) -> Unit)? = null) :
    BaseQuickAdapter<AmountPeriodBean, BaseViewHolder>(R.layout.item_loan_amount) {

    override fun convert(holder: BaseViewHolder, item: AmountPeriodBean) {
        if (type == 1) {
            holder.setText(R.id.amountTv, "₹ " + item.num)
        } else {
            holder.setText(R.id.amountTv, item.num.toString() + " Days")
        }
        val amountSelectIv = holder.getView<ImageView>(R.id.amountSelectIv)
        amountSelectIv.isVisible = item.isEnable
        holder.setGone(R.id.tipsTv, item.isEnable)
        if (item.isEnable) {
            holder.setTextColor(R.id.amountTv, ColorUtils.getColor(R.color.black))
        } else {
            holder.setTextColor(R.id.amountTv, ColorUtils.getColor(R.color.black_60))
        }

        if (item.isCheck) {
            amountSelectIv.setImageResource(R.mipmap.select_check)
        } else {
            amountSelectIv.setImageResource(R.mipmap.select_uncheck)
        }
        amountSelectIv.ktClick {
            data.forEach {
                it.isCheck = it == item
            }
            notifyDataSetChanged()
            block?.invoke(item)
        }

    }
}
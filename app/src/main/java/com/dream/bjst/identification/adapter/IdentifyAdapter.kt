package com.dream.bjst.identification.adapter

import android.view.View
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dream.bjst.R
import com.dream.bjst.identification.bean.identifyBean
import com.dream.bjst.identification.ui.ApproveContactsActivity
import com.dream.bjst.identification.ui.ApproveIdCardActivity
import com.dream.bjst.identification.ui.LivenessDetectionActivity
import com.ruffian.library.widget.RImageView
import com.ruffian.library.widget.RTextView
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow

class IdentifyAdapter :
    BaseQuickAdapter<identifyBean, BaseViewHolder>(R.layout.item_identification_recyclerview) {
    val mNameList: MutableList<identifyBean> = ArrayList()


    override fun convert(holder: BaseViewHolder, item: identifyBean) {
        mNameList.add(
            identifyBean(
                R.mipmap.identify_authentication,
                StringUtils.getString(R.string.authentication)
            )
        )
        mNameList.add(
            identifyBean(
                R.mipmap.idenfgtify_facial_verification,
                StringUtils.getString(R.string.facial_verification)
            )
        )
        mNameList.add(
            identifyBean(
                R.mipmap.emergency_contack,
                StringUtils.getString(R.string.emergency_contact)
            )
        )
        mNameList.add(
            identifyBean(
                R.mipmap.identify_bank,
                StringUtils.getString(R.string.bank_account)
            )
        )
        holder.getView<RImageView>(R.id.identify_authentication)
            .setBackgroundResource(mNameList.get(holder.adapterPosition).icon)
        holder.getView<RTextView>(R.id.identify_authentication_tv)
            .setText(mNameList.get(holder.adapterPosition).name)
        //item点击事件

        holder.itemView.ktClick {

            when (holder.adapterPosition) {
                0 -> {
                    context.ktStartActivity(ApproveIdCardActivity::class)
                    holder.getView<RelativeLayout>(R.id.item_authentication_rv)
                        .setBackgroundResource(R.color.color_identify_select)
                    holder.getView<RImageView>(R.id.identify_authentication_arrow)
                        .setImageResource(R.mipmap.itentify_select_arrow)
                }
                1 -> {
                    context.ktStartActivity(LivenessDetectionActivity::class)
                    holder.getView<RelativeLayout>(R.id.item_authentication_rv)
                        .setBackgroundResource(R.color.color_identify_select)
                    holder.getView<RImageView>(R.id.identify_authentication_arrow)
                        .setImageResource(R.mipmap.itentify_select_arrow)
                }
                2 -> {
                    context.ktStartActivity(ApproveContactsActivity::class)
                    holder.getView<RelativeLayout>(R.id.item_authentication_rv)
                        .setBackgroundResource(R.color.color_identify_select)
                    holder.getView<RImageView>(R.id.identify_authentication_arrow)
                        .setImageResource(R.mipmap.itentify_select_arrow)
                }
                3 -> {
//                          context.ktStartActivity(LivenessDetectionActivity::class)
                    holder.getView<RelativeLayout>(R.id.item_authentication_rv)
                        .setBackgroundResource(R.color.color_identify_select)
                    holder.getView<RImageView>(R.id.identify_authentication_arrow)
                        .setImageResource(R.mipmap.itentify_select_arrow)
                }
            }


        }


    }

}
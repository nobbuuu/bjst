package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.didichuxing.doraemonkit.kit.core.DokitServiceEnum
import com.dream.bjst.R
import com.dream.bjst.databinding.DialogNoticeUpgradeBinding
import com.dream.bjst.login.bean.ResAppUpdateTxtInfo
import com.tcl.base.kt.ktClick
import kotlin.concurrent.thread

/**
 * 创建日期：2022-09-16 on 10:48
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class UpgradeNoticeDialog(
    context: Context,
    val data: ResAppUpdateTxtInfo,
    val sureListener: (() -> Unit)? = null
) :
    BaseBindingDialog<DialogNoticeUpgradeBinding>(context, gravity = Gravity.CENTER) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event()
        mBinding.upgradeTitle.text = data.`809D809891`
        val contentAdapter = ContentAdapter()
        mBinding.updateContentRv.apply {
            adapter = contentAdapter
        }
        contentAdapter.setList(data.`808C80BD809199`)
    }

    private fun event() {
        takeIf {!data.`99959A9095809B868DA18490958091`}?.let {mBinding.noticeFinishImage.ktClick {
            dismiss()
            sureListener?.invoke()
        }}

        mBinding.sureUpdateNowBtn.ktClick {
            mBinding.upgradeNoticeLL.visibility = View.GONE
            mBinding.noticeFinishImage.visibility = View.GONE
            mBinding.upgradeProgress.visibility = View.VISIBLE
            //下载apk
            thread {


            }
        }
    }

    inner class ContentAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_update_content) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.upgradeListTv, item)
        }
    }
}
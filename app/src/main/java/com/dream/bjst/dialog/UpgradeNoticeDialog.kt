package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.didichuxing.doraemonkit.kit.core.DokitServiceEnum
import com.dream.bjst.databinding.DialogNoticeUpgradeBinding
import com.tcl.base.kt.ktClick

/**
 * 创建日期：2022-09-16 on 10:48
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class UpgradeNoticeDialog(context: Context, val sureListener: (() -> Unit)? = null) :
    BaseBindingDialog<DialogNoticeUpgradeBinding>(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event()

    }

    private fun event() {
        mBinding.noticeFinishImage.ktClick {
            dismiss()
            sureListener?.invoke()
        }
        mBinding.sureUpdateNowBtn.ktClick {
            mBinding.upgradeNoticeLL.visibility=View.GONE
            mBinding.noticeFinishImage.visibility=View.GONE
        }
    }
}
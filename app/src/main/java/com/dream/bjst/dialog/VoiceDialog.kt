package com.dream.bjst.dialog

import android.content.Context
import android.os.Bundle
import com.dream.bjst.databinding.DialogVoiceCodeBinding
import com.tcl.base.kt.ktClick

class VoiceDialog(context: Context,val sureListener: (() -> Unit)? = null) : BaseBindingDialog<DialogVoiceCodeBinding>(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.sureBtn.ktClick {
            dismiss()
            sureListener?.invoke()
        }
        mBinding.closeIv.ktClick {
            dismiss()
        }

    }
}
package com.dream.bjst.identification.ui

import android.os.Bundle
import com.dream.bjst.databinding.ActivityCertificationIdcardBinding
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.utils.PhotoManager
import com.dream.bjst.utils.PhotoSelectDialog
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class ActivityApproveIdCard :
    BaseActivity<IdentificationViewModel, ActivityCertificationIdcardBinding>() {
    lateinit var photoManager: PhotoManager
    override fun initView(savedInstanceState: Bundle?) {
        photoManager = PhotoManager(this)
        mBinding.frontIv.ktClick {
            PhotoSelectDialog(this, PhotoSelectDialog.PICK_AVATAR)
        }
        mBinding.backIv.ktClick {

        }
        mBinding.panCardIv.ktClick {

        }
    }

    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
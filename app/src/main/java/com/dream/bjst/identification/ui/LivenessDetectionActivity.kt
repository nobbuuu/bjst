package com.dream.bjst.identification.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dream.bjst.databinding.ActivityLivenessDetectionBinding
import com.dream.bjst.identification.vm.LivenessDetectionViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class LivenessDetectionActivity : BaseActivity<LivenessDetectionViewModel,ActivityLivenessDetectionBinding>(){


    override fun initView(savedInstanceState: Bundle?) {
     mBinding.titleLive.leftView.ktClick {
         onBackPressed()
     }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }
}
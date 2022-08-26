package com.dream.bjst.identification

import android.os.Bundle
import com.dream.bjst.R
import com.dream.bjst.databinding.ActivityIdentificationBinding
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class IdentificationActivity :
    BaseActivity<IdentificationViewModel, ActivityIdentificationBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.identifyTitle.ktClick {
            onBackPressed()
        }
        mBinding.authenticationRv.ktClick {
          mBinding.identifyAuthenticationArrow.setImageResource(R.mipmap.itentify_select_arrow)
        }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }

}
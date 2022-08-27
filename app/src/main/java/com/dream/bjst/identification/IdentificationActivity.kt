package com.dream.bjst.identification

import android.annotation.SuppressLint
import android.os.Bundle
import com.dream.bjst.R
import com.dream.bjst.databinding.ActivityIdentificationBinding
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class IdentificationActivity :
    BaseActivity<IdentificationViewModel, ActivityIdentificationBinding>() {
    var temp:Boolean = false
    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.identifyTitle.ktClick {
            onBackPressed()
        }
        mBinding.authenticationRv.ktClick {
            when(temp){
                false -> {
                    //设置Authentication
                    mBinding.authenticationRv.setBackgroundResource(R.color.color_identify_select)
                    mBinding.identifyAuthenticationArrow.setImageResource(R.mipmap.itentify_select_arrow)
                    temp=true
                }
                true -> {
                    mBinding.authenticationRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.identifyAuthenticationArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    temp=false
                }
            }

        }
    }

    override fun initData() {

    }

    override fun initDataOnResume() {



    }

}
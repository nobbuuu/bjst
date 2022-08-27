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
        //Authentication
        mBinding.authenticationRv.ktClick {
            when(temp){
                false -> {
                    //设置Authentication
                    mBinding.authenticationRv.setBackgroundResource(R.color.color_identify_select)
                    mBinding.identifyAuthenticationArrow.setImageResource(R.mipmap.itentify_select_arrow)
                    //
                    mBinding.facialRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.facialAuthenticationArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    //
                    mBinding.emergencyContactRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.emergencyContactArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    //
                    mBinding.bankCountRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.bankCountArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    temp=true
                }
                true -> {
                    mBinding.authenticationRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.identifyAuthenticationArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    temp=false
                }
            }

        }
        //Facial verification
        mBinding.facialRv.ktClick {

            when(temp){
               false -> {
                   //设置Facial verification
                   mBinding.facialRv.setBackgroundResource(R.color.color_identify_select)
                   mBinding.facialAuthenticationArrow.setImageResource(R.mipmap.itentify_select_arrow)
                   temp=true
               }
                true -> {
                    mBinding.facialRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.facialAuthenticationArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    temp=false
                }
            }

        }
        //Emergency contact
        mBinding.emergencyContactRv.ktClick {
            when(temp){
                //Emergency contact
                false -> {
                    mBinding.emergencyContactRv.setBackgroundResource(R.color.color_identify_select)
                    mBinding.emergencyContactArrow.setImageResource(R.mipmap.itentify_select_arrow)
                    temp=true
                }
                true -> {
                    mBinding.emergencyContactRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.emergencyContactArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
                    temp=false
                }
            }
        }
        //bankAccount
        mBinding.bankCountRv.ktClick {
            when(temp){

                false -> {
                    mBinding.bankCountRv.setBackgroundResource(R.color.color_identify_select)
                    mBinding.bankCountArrow.setImageResource(R.mipmap.itentify_select_arrow)
                    temp=true
                }
                true->{
                    mBinding.bankCountRv.setBackgroundResource(R.color.color_identify_unselect)
                    mBinding.bankCountArrow.setImageResource(R.mipmap.itentify_unselect_arrow)
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
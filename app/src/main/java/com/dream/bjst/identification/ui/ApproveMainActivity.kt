package com.dream.bjst.identification.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.dream.bjst.R

import com.dream.bjst.databinding.ActivityIdentificationBinding
import com.dream.bjst.identification.adapter.IdentifyAdapter
import com.dream.bjst.identification.bean.identifyBean
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class ApproveMainActivity :
    BaseActivity<IdentificationViewModel, ActivityIdentificationBinding>() {
    val mNameList: MutableList<identifyBean> = ArrayList()

    var identifyAdapter = IdentifyAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.adjustWindow(this, color = R.color.color_F8FFF0, mBinding.rootLay)
        mBinding.identifyTitle.ktClick {
            onBackPressed()
        }

        mBinding.identifyGetLoanBtn.ktClick {
            viewModel.idCardStatus.value?.let {
                //当前进行到的项目类型,null表示都通过，10：身份证前ocr（跳转到证件ocr界面）,11:身份证后ocr（跳转到证件ocr界面）,20:身份证前ocr（跳转到证件ocr界面）,30:pan卡ocr（跳转到证件ocr界面）,31:pan号认证（跳转到证件ocr界面）,40:活体检测,50:人脸对比,51:人脸对比（跳转到活体）,60:紧急联系人认证,70:扩展信息录入,80:银行卡绑定，90：银行卡重绑定
                when(it.`9A919190B09B9D9A93BD809199`){
                    "10","11","20","30","31" ->{
                        ktStartActivity(ApproveIdCardActivity::class)
                    }
                    "40","50","51" ->{
                        ktStartActivity(LivenessDetectionActivity::class)
                    }
                    "60" ->{
                        ktStartActivity(ApproveContactsActivity::class)
                    }
                    "70" ->{
                        ktStartActivity(ApproveIdCardConfirmActivity::class)
                    }
                    "80","90" ->{
                        ktStartActivity(ApproveBankCardActivity::class)
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun initData() {
        mBinding.identifyRecyclerview.layoutManager = GridLayoutManager(this, 2)
        repeat(4) {
            mNameList.add(identifyBean(R.mipmap.identify_bank, "Authentication"))
        }
        identifyAdapter.setList(mNameList)
        mBinding.identifyRecyclerview.adapter = identifyAdapter

        viewModel.fetchCustomerKycStatus()

    }

    override fun initDataOnResume() {

        viewModel.idCardStatus.observe(this){

        }
    }

}
package com.dream.bjst.identification.ui

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.databinding.ActivityContactsApproveBinding
import com.dream.bjst.databinding.ActivityIdcardConfirmBinding
import com.dream.bjst.identification.bean.IdCardConfirmParam
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class ApproveIdCardConfirmActivity :
    BaseActivity<IdentificationViewModel, ActivityIdcardConfirmBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        mBinding.sureBtn.ktClick {
            val bean = IdCardConfirmParam().apply {
                `84959ABA8199969186` = mBinding.panNo.getEndEdtText()
                `95909086918787` = mBinding.address.getEndEdtText()
                `969D86809C90958D` = mBinding.birthday.getEndEdtText()
                `9199959D98` = mBinding.email.getEndEdtText()
                `9D90B7958690BA8199969186` = mBinding.aadNo.getEndEdtText()
                `999B909D928DBD90B7958690BA959991` = mBinding.nameAad.getEndEdtText()
                `999B909D928DA4959ABA959991` = mBinding.namePan.getEndEdtText()
            }
            val toJson = GsonUtils.toJson(bean)
            LogUtils.dTag("jsonParam",toJson)
            viewModel.submitAdjustInfo(toJson)
        }
    }

    override fun initData() {
        viewModel.fetchCustomerIdCardInfo()
    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()

        viewModel.idCardDetails.observe(this){
            mBinding.aadNo.setEndEdtText(it.`9590909586B7958690BA8199969186`)
            mBinding.address.setEndEdtText(it.`95909086918787`)
            mBinding.birthday.setEndEdtText(it.`969D86809C90958D`)
            mBinding.nameAad.setEndEdtText(it.`9A959991B2869B99B59590909586`)
            mBinding.namePan.setEndEdtText(it.`9A959991BD9AA4959A`)
            mBinding.panNo.setEndEdtText(it.`84959ABD90BA8199969186`)
            mBinding.pinCode.setEndEdtText(it.`849D9AB79B9091`)
            mBinding.gender.setEndEdtText(transferSex(it.`87918C`))
        }

        viewModel.confirmResult.observe(this){
            if (it.`869187819880`){
                ktStartActivity(LivenessDetectionActivity::class)
                finish()
            }else{
                "confirm failed,please try again"
            }
        }
    }

    fun transferSex(sex:Int?) : String{
        var sexStr = ""
        when (sex){
            0 ->{
                sexStr = "unknown"
            }
            10 ->{
                sexStr = "male"
            }
            20 ->{
                sexStr = "female"
            }
            30 ->{
                sexStr = "transgender"
            }
        }
        return sexStr
    }

}
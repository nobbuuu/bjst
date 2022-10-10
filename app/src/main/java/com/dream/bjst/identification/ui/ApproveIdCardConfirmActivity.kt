package com.dream.bjst.identification.ui

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityIdcardConfirmBinding
import com.dream.bjst.identification.bean.IdCardConfirmParam
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow
import com.tcl.base.kt.nullToEmpty

class ApproveIdCardConfirmActivity :
    BaseActivity<IdentificationViewModel, ActivityIdcardConfirmBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        mBinding.sureBtn.ktClick {
            if (mBinding.email.getEndEdtText().isNotEmpty()){
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
            }else{
                "Please enter your email address".ktToastShow()
            }
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
            mBinding.nameAad.setEndEdtText(it.`9A959991B2869B99B59590909586`.nullToEmpty())
            mBinding.namePan.setEndEdtText(it.`9A959991BD9AA4959A`.nullToEmpty())

            mBinding.aadNo.setEndText(it.`9590909586B7958690BA8199969186`.nullToEmpty())
            mBinding.address.setEndText(it.`95909086918787`.nullToEmpty())
            mBinding.birthday.setEndText(it.`969D86809C90958D`.nullToEmpty())
            mBinding.panNo.setEndText(it.`84959ABD90BA8199969186`.nullToEmpty())
            mBinding.pinCode.setEndText(it.`849D9AB79B9091`.nullToEmpty())
            mBinding.gender.setEndText(transferSex(it.`87918C`))
        }

        viewModel.confirmResult.observe(this){
            if (it.`869187819880`){
                //缓存姓名
                //UserManager.setUserName(mBinding.aadNo.getEndEdtText())
                ktStartActivity(LivenessDetectionActivity::class)
                finish()
            }else{
                "confirm failed,please try again".ktToastShow()
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
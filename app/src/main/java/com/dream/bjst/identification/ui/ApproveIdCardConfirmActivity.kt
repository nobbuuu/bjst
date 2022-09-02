package com.dream.bjst.identification.ui

import android.os.Bundle
import com.dream.bjst.databinding.ActivityContactsApproveBinding
import com.dream.bjst.databinding.ActivityIdcardConfirmBinding
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity

class ApproveIdCardConfirmActivity :
    BaseActivity<IdentificationViewModel, ActivityIdcardConfirmBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
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
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


        }
    }

}
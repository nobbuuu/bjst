package com.dream.bjst.repayment.ui

import android.os.Bundle
import com.dream.bjst.databinding.ActivityExtendRePaymentBinding
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

/**
 * 创建日期：2022-09-05 on 0:47
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class ExtendRePaymentActivity:BaseActivity<RepaymentViewModel,ActivityExtendRePaymentBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }
    }

    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
package com.dream.bjst.repayment.ui

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils

import com.dream.bjst.databinding.ActivityExtendRePaymentBinding
import com.dream.bjst.repayment.bean.RepaymentDetailParam
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.bigDecimalMinus
import com.tcl.base.kt.bigDecimalPlus
import com.tcl.base.kt.ktClick

/**
 * 创建日期：2022-09-05 on 0:47
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class ExtendRePaymentActivity : BaseActivity<RepaymentViewModel, ActivityExtendRePaymentBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }
        //添加参数
        var param: String = GsonUtils.toJson(
            RepaymentDetailParam(
                `969B86869B83BD90` = intent.getStringExtra("borrowId")
            )
        )
        viewModel.paymentExtendData(param)
    }

    /**
     * 数据更新
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentExtendResult.observe(this) {

            mBinding.loanAmount.text = "₹ " + it.`84869D9A979D849598B5999B819A80`
            mBinding.expirationTime.text = it.`869184958DB19A90`
            it.`869B9898A691A4958DBD9A929BA0918699B89D8780`.getOrNull(0)?.let { bean ->
                mBinding.paymentAmount.text = "₹ " + bean.`84959D90B5999B819A80`//.bigDecimalPlus(bean.`9B829186908191B5999B819A80`)
                mBinding.extendPaymentPeriod.text = bean.`909198958DB19A90`
                mBinding.extendPaymentFee.text = "₹ " + bean.`909198958DB5999B819A80`
                mBinding.overDueAmount.text = "₹ " + bean.`9B829186908191B5999B819A80`
                mBinding.dueTimeAfterExtension.text = bean.`909198958DB0958D87` + "days"
            }
        }
    }

    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
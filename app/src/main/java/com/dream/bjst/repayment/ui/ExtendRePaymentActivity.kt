package com.dream.bjst.repayment.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.ToastUtils

import com.dream.bjst.databinding.ActivityExtendRePaymentBinding
import com.dream.bjst.other.WebViewActivity
import com.dream.bjst.repayment.bean.RepaymentDetailParam
import com.dream.bjst.repayment.bean.requestRepaymentParam
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.bigDecimalMinus
import com.tcl.base.kt.bigDecimalPlus
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

/**
 * 创建日期：2022-09-05 on 0:47
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class ExtendRePaymentActivity : BaseActivity<RepaymentViewModel, ActivityExtendRePaymentBinding>() {
    var borrowId: String = ""
    var extendId: Int = 0
    override fun initView(savedInstanceState: Bundle?) {

        borrowId = intent.getStringExtra("borrowId").toString()
        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }
        //添加参数
        var param: String = GsonUtils.toJson(
            RepaymentDetailParam(
                `969B86869B83BD90` = borrowId
            )
        )
        viewModel.paymentExtendData(param)
        event()

    }


    /**
     * 事件响应
     */
    private fun event() {
        mBinding.extendRepayNow.ktClick {

            var reqPaymentParam = GsonUtils.toJson(
                requestRepaymentParam(
                    `969B86869B83BD90` = borrowId,
                    `869184958DA08D8491` = 30
                )
            )
            viewModel.reqPaymentData(reqPaymentParam)

        }
    }

    /**
     * 延期数据更新
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentExtendResult.observe(this) {

            mBinding.loanAmount.text = "₹ " + it.`84869D9A979D849598B5999B819A80`
            mBinding.expirationTime.text = it.`869184958DB19A90`
            it.`869B9898A691A4958DBD9A929BA0918699B89D8780`.getOrNull(0)?.let { bean ->
                mBinding.paymentAmount.text =
                    "₹ " + bean.`84959D90B5999B819A80`//.bigDecimalPlus(bean.`9B829186908191B5999B819A80`)
                mBinding.extendPaymentPeriod.text = bean.`909198958DB19A90`
                mBinding.extendPaymentFee.text = "₹ " + bean.`909198958DB5999B819A80`
                mBinding.overDueAmount.text = "₹ " + bean.`9B829186908191B5999B819A80`
                mBinding.dueTimeAfterExtension.text = bean.`909198958DB0958D87` + " days"
            }
        }

        /**
         * 延期还款请求
         */
        viewModel.reqRepaymentResult.observe(this) {
            when (it.`84958DA08D8491`) {
                "0" -> {
                    ktStartActivity(WebViewActivity::class) {
                        putExtra("webUrl", it.`84958DB89D9A9F`)
                        putExtra("title", "Extend")
                        putExtra("jsEnable", true)
                    }
                }
                "1" -> {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.data = (Uri.parse(it.`84958DB89D9A9F`))
                        startActivity(intent)
                    } catch (e: Exception) {
                        println("There is no browser on the current phone!")
                    }
                }
            }
        }

    }

    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
package com.dream.bjst.loan.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.dream.bjst.databinding.FragmentLoanBinding
import com.dream.bjst.dialog.AmountDialog
import com.dream.bjst.loan.adapter.LoanProductAdapter
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.dream.bjst.loan.vm.LoanViewModel
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.weiget.recylerview.RecycleViewDivider

class LoanFragment : BaseFragment<LoanViewModel, FragmentLoanBinding>() {

    lateinit var loanAdapter: LoanProductAdapter
    lateinit var mAmountDialog: AmountDialog
    lateinit var mPeriodDialog: AmountDialog
    private val amountList = arrayListOf<AmountPeriodBean>()
    private val periodList = arrayListOf<AmountPeriodBean>()
    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.userIcon)
        loanAdapter = LoanProductAdapter()
        mAmountDialog = AmountDialog(requireContext())
        mPeriodDialog = AmountDialog(requireContext(), type = 2)
        mBinding.otherLoanRv.apply {
            adapter = loanAdapter
            addItemDecoration(RecycleViewDivider(requireContext(), LinearLayoutManager.VERTICAL))
        }

        viewModel.fetchProducts()
        onEvent()
    }


    fun onEvent() {
        mBinding.ordersLay.ktClick {
            ktStartActivity(LoanRecordsActivity::class)
        }
        mBinding.amountLay.ktClick {
            mAmountDialog.setData(amountList).show()
        }
        mBinding.periodLay.ktClick {
            mPeriodDialog.setData(periodList).show()
        }
        //选择贷款金额后刷新产品列表
        mAmountDialog.setOnSelectListener {
            mBinding.amountTv.text = "₹ " + it.num
            var tempAmount = 0
            var amountReceive = 0
            var repayAmount = 0
            loanAdapter.data.forEachIndexed { index, bean ->
                tempAmount += bean.`989B959AB5999B819A80`
                bean.isCheck = tempAmount <= it.num
                if (bean.isCheck) {
                    amountReceive += bean.`869197919D8291B5999B819A80`
                    repayAmount += bean.`9A919190A69184958DB5999B819A80`
                }
            }
            loanAdapter.notifyDataSetChanged()
            mBinding.amountReceiveNum.text = "₹ $amountReceive"
            mBinding.repayAmount.text = "₹ $repayAmount"
        }
        mPeriodDialog.setOnSelectListener {
            mBinding.amountTv.text = it.num.toString() + " Days"
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.loanData.observe(this) {

            //刷新Amount弹框数据和产品列表数据
            val defaultChooseNum = it.`90919295819880A79198919780A4869B90819780B79B819A80`
            it.`84869B90819780B89D8780`?.forEachIndexed { index, bean ->
                bean.isCheck = index < defaultChooseNum
            }
            loanAdapter.setList(it.`84869B90819780B89D8780`)

            var amount = 0
            var amountReceive = 0
            var repayAmount = 0
            amountList.clear()

            loanAdapter.data.forEachIndexed { index, bean ->
                amount += bean.`989B959AB5999B819A80`
                amountList.add(
                    AmountPeriodBean(
                        num = amount,
                        isEnable = index < it.`99958CB89B959AA4869B90819780B79B819A80`,
                        isCheck = defaultChooseNum == index + 1
                    )
                )
                if (index < defaultChooseNum) {
                    amountReceive += bean.`869197919D8291B5999B819A80`
                    repayAmount += bean.`9A919190A69184958DB5999B819A80`
                }
            }
            if (amountList.size >= defaultChooseNum) {
                mBinding.amountTv.text = "₹ " + amountList[defaultChooseNum - 1].num
                mBinding.amountReceiveNum.text = "₹ $amountReceive"
                mBinding.repayAmount.text = "₹ $repayAmount"
                mBinding.repaymentDate.text = it.`869184958DB0958091`
            }

            //借款周期
            periodList.clear()
            it.`989B959AA6959A939187`.forEachIndexed { index, bean ->
                periodList.add(
                    AmountPeriodBean(
                        num = bean.`989B959AA6959A9391`,
                        isEnable = bean.`919A95969891`,
                        isCheck = index == 0
                    )
                )
            }
            if (periodList.isNotEmpty()) {
                mBinding.days.text = periodList[0].num.toString() + " Days"
            }
        }
    }

}
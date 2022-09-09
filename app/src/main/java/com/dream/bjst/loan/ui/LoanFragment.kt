package com.dream.bjst.loan.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.databinding.FragmentLoanBinding
import com.dream.bjst.loan.adapter.OtherLoanAdapter
import com.dream.bjst.loan.vm.LoanViewModel
import com.dream.bjst.utils.DateUtils
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.weiget.recylerview.RecycleViewDivider

class LoanFragment : BaseFragment<LoanViewModel, FragmentLoanBinding>() {

    private val otherLoanAdapter = OtherLoanAdapter()
    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.userIcon)
        mBinding.otherLoanRv.apply {
            adapter = otherLoanAdapter
            addItemDecoration(RecycleViewDivider(requireContext(), LinearLayoutManager.VERTICAL))
        }
        val data = arrayListOf<EmptyBean>()
        repeat(15) {
            data.add(EmptyBean())
        }
        otherLoanAdapter.setList(data)
        mBinding.ordersLay.ktClick {
            ktStartActivity(LoanRecordsActivity::class)
        }
        viewModel.fetchHomeInfo()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.homeData.observe(this) {
            mBinding.amountTv.text = "₹ " + it.`989B959AB5999B819A80`
            when (it.`8491869B9D90A19A9D80`) {
                10 -> {//天
                    mBinding.days.text = it.`8491869B9D90`.toString() + " Days"
                }
                20 -> {//月
                    mBinding.days.text = it.`8491869B9D90`.toString() + " months"
                }
            }
            mBinding.repaymentDate.text = DateUtils.getSomeDate(it.`8491869B9D90` ?: 90, it.`8491869B9D90A19A9D80`)
            mBinding.amountReceiveNum.text = "₹ " + it.`869197919D8291B5999B819A80`
            mBinding.periodNum.text = "₹ " + it.`879186829D9791B5999B819A80`
        }
    }

}
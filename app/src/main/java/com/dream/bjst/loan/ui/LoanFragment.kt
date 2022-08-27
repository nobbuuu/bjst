package com.dream.bjst.loan.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.databinding.FragmentLoanBinding
import com.dream.bjst.loan.adapter.OtherLoanAdapter
import com.dream.bjst.loan.vm.LoanViewModel
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
        viewModel.loginResult
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.loginResult.observe(this) {

        }
    }

}
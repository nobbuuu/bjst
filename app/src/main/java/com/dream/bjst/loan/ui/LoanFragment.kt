package com.dream.bjst.loan.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.databinding.FragmentLoanBinding
import com.dream.bjst.loan.adapter.OtherLoanAdapter
import com.tcl.base.common.BaseViewModel
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.weiget.recylerview.RecycleViewDivider

class LoanFragment : BaseFragment<BaseViewModel, FragmentLoanBinding>() {

    private val otherLoanAdapter = OtherLoanAdapter()
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.otherLoanRv.apply {
            adapter = otherLoanAdapter
            addItemDecoration(RecycleViewDivider(requireContext(),LinearLayoutManager.VERTICAL))
        }
        val data = arrayListOf<EmptyBean>()
        repeat(15){
            data.add(EmptyBean())
        }
        otherLoanAdapter.setList(data)
    }

}
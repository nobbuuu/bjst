package com.kredit.cash.loan.app.identification.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kredit.cash.loan.app.databinding.ActivityCommonRvBinding
import com.kredit.cash.loan.app.identification.adapter.BankListAdapter
import com.kredit.cash.loan.app.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.weiget.recylerview.RecycleViewDivider

class ApproveBankListActivity : BaseActivity<IdentificationViewModel, ActivityCommonRvBinding>() {

    val bankAdapter = BankListAdapter()

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.commonRv.apply {
            adapter = bankAdapter
            addItemDecoration(
                RecycleViewDivider(
                    this@ApproveBankListActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        bankAdapter.setOnItemClickListener { adapter, view, position ->
            viewModel.bankList.value?.get(position)?.let {
                intent.putExtra("bank", it)
                setResult(999, intent)
                finish()
            }
        }

        mBinding.smartRefresh.setOnRefreshListener {
            viewModel.fetchBanks()
        }


    }

    override fun initData() {
        viewModel.fetchBanks()
    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.bankList.observe(this) {
            mBinding.smartRefresh.finishRefresh()
            bankAdapter.setList(it)
        }
    }

}
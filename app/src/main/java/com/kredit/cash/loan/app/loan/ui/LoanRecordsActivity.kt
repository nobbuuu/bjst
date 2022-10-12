package com.kredit.cash.loan.app.loan.ui

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.kredit.cash.loan.app.bean.CommonPageBean
import com.kredit.cash.loan.app.databinding.ActivityLoanRecordsBinding
import com.kredit.cash.loan.app.loan.adapter.LoanRecordsAdapter
import com.kredit.cash.loan.app.loan.bean.OrderResultBean
import com.kredit.cash.loan.app.loan.vm.LoanViewModel
import com.tcl.base.common.ui.BaseActivity

class LoanRecordsActivity : BaseActivity<LoanViewModel, ActivityLoanRecordsBinding>() {

    private var curPage = 1
    val mLoanRecordsAdapter = LoanRecordsAdapter {
        setResult(it * 10)
        finish()
    }
    private val dataList = arrayListOf<OrderResultBean>()
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.loanRecords.apply {
            adapter = mLoanRecordsAdapter
        }
        mBinding.smartRefresh.setOnRefreshListener {
            refreshData()
        }
        mBinding.smartRefresh.setOnLoadMoreListener {
            refreshData(true)
        }
    }


    override fun initData() {
        refreshData()
    }

    fun refreshData(isMore: Boolean = false) {
        if (isMore) {
            curPage++
        } else {
            curPage = 1
        }
        viewModel.fetchOrderHistory(GsonUtils.toJson(CommonPageBean(curPage, 20)))
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.historyData.observe(this) {
            mBinding.smartRefresh.finishRefresh()
            mBinding.smartRefresh.finishLoadMore()
            val list = arrayListOf<OrderResultBean>()
            com.kredit.cash.loan.app.utils.DataUtils.groupByMode("958484988DA09D9991", it.`9B86909186B89D8780`)
                .forEach { data ->
                    list.add(
                        OrderResultBean(
                            applyTime = data.key,
                            `9B86909186B89D8780` = data.value
                        )
                    )
                }
            if (curPage == 1) {
                dataList.clear()
            }
            dataList.addAll(list)
            mLoanRecordsAdapter.setList(dataList)
        }
    }

    override fun initDataOnResume() {


    }
}
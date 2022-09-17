package com.dream.bjst.loan.ui

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.bean.CommonPageBean
import com.dream.bjst.databinding.ActivityLoanRecordsBinding
import com.dream.bjst.loan.adapter.LoanRecordsAdapter
import com.dream.bjst.loan.bean.OrderResultBean
import com.dream.bjst.loan.vm.LoanViewModel
import com.dream.bjst.utils.DataUtils
import com.tcl.base.common.ui.BaseActivity

class LoanRecordsActivity : BaseActivity<LoanViewModel, ActivityLoanRecordsBinding>() {

    private var curPage = 1
    val mLoanRecordsAdapter = LoanRecordsAdapter()
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

    override fun initDataOnResume() {

    }

    override fun startObserve() {
        super.startObserve()
        viewModel.historyData.observe(this){
            mBinding.smartRefresh.finishRefresh()
            mBinding.smartRefresh.finishLoadMore()
            val list = arrayListOf<OrderResultBean>()
            DataUtils.groupByMode("`958484988DA09D9991`",it.`9B86909186B89D8780`).forEach {data ->
                list.add(OrderResultBean(applyTime = data.key, `9B86909186B89D8780` = data.value))
            }
            mLoanRecordsAdapter.setList(list)
        }
    }
}
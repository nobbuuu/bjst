package com.dream.bjst.repayment.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.R

import com.dream.bjst.databinding.FragmentRepaymentBinding
import com.dream.bjst.repayment.adapter.*
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktSeriesClick
import com.tcl.base.kt.ktStartActivity
import rxhttp.wrapper.param.IFile

/**
 * 创建日期：2022-09-05 on 0:50
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class RepaymentFragment : BaseFragment<RepaymentViewModel, FragmentRepaymentBinding>() {
    //数据适配器
    var overDueAdapter = OverDueAdapter()
    var dueTodayAdapter = DueTodayAdapter()
    var notDueAdapter = NotDueAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.repayment)
        initRv()
        viewModel.repaymentData()
        mBinding.smartRefresh.setOnRefreshListener {
            viewModel.repaymentData()
        }
    }

    /**
     * 数据更新
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentResult.observe(this) {
            mBinding.smartRefresh.finishRefresh()
            mBinding.overdueRl.isVisible = !it.`9B829186908191BB8690918687`.isNullOrEmpty()
            if (!it.`9B829186908191BB8690918687`.isNullOrEmpty()) {
                overDueAdapter.setList(it.`9B829186908191BB8690918687`)
                mBinding.overDueNotice.text = "Daily penalty interest rate ${
                    it.`9B829186908191BB8690918687`[0].`9B829186908191BD9A809186918780A6958091`.toFloatOrNull()
                        ?.times(100)?.toUInt()
                } %"
            }
            mBinding.dueTodayRl.isVisible = !it.`908191A09B90958DBB8690918687`.isNullOrEmpty()
            if (!it.`908191A09B90958DBB8690918687`.isNullOrEmpty()) {
                dueTodayAdapter.setList(it.`908191A09B90958DBB8690918687`)
            }
            mBinding.notDueRl.isVisible = !it.`9A9B80B08191BB8690918687`.isNullOrEmpty()
            if (!it.`9A9B80B08191BB8690918687`.isNullOrEmpty()) {
                notDueAdapter.setList(it.`9A9B80B08191BB8690918687`)
            }

            val noData =
                overDueAdapter.data.isEmpty() && dueTodayAdapter.data.isEmpty() && notDueAdapter.data.isEmpty()
            mBinding.dataLay.isVisible = !noData
            mBinding.emptyLay.rootLay.isVisible = noData
        }
    }


    private fun initRv() {

        mBinding.overDueRv.adapter = overDueAdapter
        mBinding.dueTodayRv.adapter = dueTodayAdapter
        mBinding.notDueRv.adapter = notDueAdapter
        //点击事件
        event()
    }

    private fun event() {
        mBinding.emptyLay.btnLoadData.ktClick {
            Navigation.findNavController(mBinding.emptyLay.rootLay)
                .navigate(R.id.repayment_to_navigation_loan)
        }
        overDueAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.settleNowBtn ->
                    ktStartActivity(RepaymentDetailActivity::class) {
                        putExtra("detailId", overDueAdapter.data[position].`969B86869B83BD90`)
                    }
            }
        }
        notDueAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.settleNowBtn -> ktStartActivity(RepaymentDetailActivity::class) {
                    putExtra("detailId", notDueAdapter.data[position].`969B86869B83BD90`)
                }
            }

        }
        dueTodayAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.settleNowBtn -> ktStartActivity(RepaymentDetailActivity::class) {
                    putExtra("detailId", dueTodayAdapter.data[position].`969B86869B83BD90`)
                }
            }

        }


    }
}
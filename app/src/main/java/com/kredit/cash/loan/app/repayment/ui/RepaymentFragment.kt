package com.kredit.cash.loan.app.repayment.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.BarUtils
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.common.UserManager

import com.kredit.cash.loan.app.databinding.FragmentRepaymentBinding
import com.kredit.cash.loan.app.main.homeType
import com.kredit.cash.loan.app.repayment.adapter.*
import com.kredit.cash.loan.app.repayment.bean.RepaymentBean
import com.kredit.cash.loan.app.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

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
        mBinding.smartRefresh.setOnRefreshListener {
            viewModel.repaymentData()
        }
        val data = arguments?.getSerializable("reData")?.let {
            if (it is RepaymentBean) {
                viewModel.repaymentResult.postValue(it)
            }
        }
        if (data == null) {
            viewModel.repaymentData()
        }
    }

    /**
     * 数据更新
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentResult.observe(this) {
            if (UserManager.isLogin()) {
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
        viewModel.refreshResult.observe(this) {
            mBinding.smartRefresh.finishRefresh()
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
            if (homeType == Constant.ACTION_TYPE_MAIN){
                findNavController().navigate(R.id.navigation_loan)
            }else{
                findNavController().navigate(R.id.navigation_home)
            }
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
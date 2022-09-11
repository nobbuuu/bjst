package com.dream.bjst.repayment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.blankj.utilcode.util.BarUtils
import com.dream.bjst.R

import com.dream.bjst.databinding.FragmentRepaymentBinding
import com.dream.bjst.repayment.adapter.*
import com.dream.bjst.repayment.bean.OverdueOrder
import com.dream.bjst.repayment.bean.RepaymentBean
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.utils.MmkvUtil


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
        viewModel.repaymentData()
        initRv()
    }

    /**
     * 数据更新界面
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentResult.observe(this) {
            overDueAdapter.setList(it.`9B829186908191BB8690918687`)
            dueTodayAdapter.setList(it.`908191A09B90958DBB8690918687`)
            notDueAdapter.setList(it.`9A9B80B08191BB8690918687`)
            val noData = overDueAdapter.data.isEmpty() && dueTodayAdapter.data.isEmpty() && notDueAdapter.data.isEmpty()
            mBinding.dataLay.isVisible = !noData
            mBinding.emptyLay.rootLay.isVisible = noData
        }
    }


    private fun initRv() {


        mBinding.overDueRv.adapter = overDueAdapter
        mBinding.dueTodayRv.adapter = dueTodayAdapter
        mBinding.notDueRv.adapter = notDueAdapter
        //点击时间
        event()
    }

    private fun event() {
        mBinding.emptyLay.rootLay.ktClick {
            Navigation.findNavController(mBinding.emptyLay.rootLay)
                .navigate(R.id.repayment_to_navigation_loan)
        }
    }
}
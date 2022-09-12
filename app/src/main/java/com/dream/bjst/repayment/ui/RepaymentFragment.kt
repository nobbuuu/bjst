package com.dream.bjst.repayment.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.R

import com.dream.bjst.databinding.FragmentRepaymentBinding
import com.dream.bjst.repayment.adapter.*
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktSeriesClick
import com.tcl.base.kt.ktStartActivity

/**
 * 创建日期：2022-09-05 on 0:50
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class RepaymentFragment : BaseFragment<RepaymentViewModel, FragmentRepaymentBinding>() {
    var repayId:String?=null

    //数据适配器
    var overDueAdapter = OverDueAdapter()
    var dueTodayAdapter = DueTodayAdapter()
    var notDueAdapter = NotDueAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.repayment)
        initRv()
        viewModel.repaymentData()
    }

    /**
     * 数据更新
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentResult.observe(this) {
            overDueAdapter.setList(it.`9B829186908191BB8690918687`)
            dueTodayAdapter.setList(it.`908191A09B90958DBB8690918687`)
            notDueAdapter.setList(it.`9A9B80B08191BB8690918687`)
            //获取界面编号
           repayId= it.`9B829186908191BB8690918687`.firstOrNull()?.`969B86869B83BD90`.toString()

            val noData = overDueAdapter.data.isEmpty() && dueTodayAdapter.data.isEmpty() && notDueAdapter.data.isEmpty()
            mBinding.dataLay.isVisible = !noData
            mBinding.emptyLay.rootLay.isVisible = noData

            ToastUtils.showShort(repayId)
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




        overDueAdapter.setOnItemClickListener { adapter, view, position ->
               ktStartActivity(RepaymentDetailActivity::class){
                   this.putExtra("repayId",repayId)
               }


        }
        notDueAdapter.setOnItemClickListener { adapter, view, position ->

            ktStartActivity(RepaymentDetailActivity::class){

            }

        }
        dueTodayAdapter.setOnItemClickListener { adapter, view, position ->

            ktStartActivity(RepaymentDetailActivity::class){

            }

        }


    }
}
package com.dream.bjst.repayment.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.R

import com.dream.bjst.databinding.FragmentRepaymentBinding
import com.dream.bjst.repayment.adapter.RePaymentAdapter
import com.dream.bjst.repayment.adapter.RePaymentInAdapter
import com.dream.bjst.repayment.bean.RepaymentBean
import com.dream.bjst.repayment.bean.RepaymentInDetailBean
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.utils.MmkvUtil


/**
 * 创建日期：2022-09-05 on 0:50
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class RepaymentFragment:BaseFragment<RepaymentViewModel,FragmentRepaymentBinding>() {
    var mRePaymentAdapter: RePaymentAdapter? = null
    var mRePaymentInAdapter: RePaymentInAdapter? = null
    var mList: MutableList<RepaymentBean>? = null

    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.repayment)
        viewModel.repaymentData()
    }

    /**
     * 数据更新界面
     */
    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentResult.observe(this){
            Log.i(TAG, "startObserve: "+it.`908191A09B90958DBB8690918687`.size)



        }
    }

    override fun initDataOnViewCreated() {
        super.initDataOnViewCreated()
        mRePaymentAdapter = RePaymentAdapter()
        mRePaymentInAdapter = RePaymentInAdapter()

        // 显示空布局
        getEmptyView()?.let { mRePaymentAdapter?.setEmptyView(it) }
        mBinding.repaymentRvPlan.adapter=mRePaymentAdapter
        setData()
    }
    private fun setData() {
        mList = ArrayList()
        val inList: MutableList<RepaymentInDetailBean> = ArrayList()
        for (i in 0..2) {
            inList.add(RepaymentInDetailBean(11,"",22,33,44,"",55,66,""))
        }
        for (i in 0..9) {
//            (mList as ArrayList<RepaymentBean>).add(RepaymentBean(inList))
        }
        mRePaymentAdapter?.setList(mList)
        mRePaymentInAdapter?.setList(inList)

        //点击时间
        event()
    }
    private fun event() {
        mRePaymentInAdapter?.setOnItemChildClickListener { adapter, view, position ->
            startActivity(
                Intent(activity, RepaymentDetailActivity::class.java)
            )
        }
        mRePaymentAdapter?.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent(activity, RepaymentDetailActivity::class.java)
            )
        }
    }

    /**
     * 获取空布局
     */
    private fun getEmptyView(): View? {
        val view: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.item_repayment_empty, null, false)
        val btnLoadData = view.findViewById<Button>(R.id.btn_load_data)
        btnLoadData.setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.repayment_to_navigation_loan)
        }
        return view
    }
}
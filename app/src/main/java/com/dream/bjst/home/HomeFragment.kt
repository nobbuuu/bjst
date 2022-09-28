package com.dream.bjst.home

import android.os.Bundle
import com.dream.bjst.R
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.FragmentHomeBinding
import com.dream.bjst.home.vm.HomeViewModel
import com.dream.bjst.identification.ui.ApproveMainActivity
import com.dream.bjst.login.LoginActivity
import com.dream.bjst.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class HomeFragment:BaseFragment<HomeViewModel,FragmentHomeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.adjustWindow(requireActivity(), R.color.color_F8FFF0, mBinding.topLay)
        //当前页面是未登录展示，如果已经登录将不展示
        mBinding.homeRequestBtn.ktClick {
            if (UserManager.isLogin()) {
                ktStartActivity(ApproveMainActivity::class)
            } else {
                ktStartActivity(LoginActivity::class)
            }
        }
        viewModel.fetchHomeInfo()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.homeData.observe(this) {
            mBinding.amountNum.text = "₹ " + it.`989B959AB5999B819A80`
            when (it.`8491869B9D90A19A9D80`) {
                10 -> {//天
                    mBinding.period.text = it.`8491869B9D90`.toString() + " Days"
                }
                20 -> {//月
                    mBinding.period.text = it.`8491869B9D90`.toString() + " months"
                }
            }
            mBinding.amountReceive.text = "₹ " + it.`869197919D8291B5999B819A80`
            mBinding.homeRupee.text = "₹ " + it.`879186829D9791B5999B819A80`
            mBinding.homePercentValue.text = it.`8D919586A6958091` + "%p.a."
            mBinding.pm.text = "Total Interest@" + it.`999B9A809C87A6958091` + "%p.m."

        }
    }
}
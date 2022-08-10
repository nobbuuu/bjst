package com.dream.bjst.home.ui

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.dream.bjst.databinding.FragmentHomeBinding
import com.dream.bjst.home.vm.AreaViewModel
import com.tcl.base.common.ui.BaseFragment

/**
 *@author tiaozi
 *@date   2022/1/26
 *description 首页
 */
class HomeFragment : BaseFragment<AreaViewModel, FragmentHomeBinding> (){

    private val columnsTitle = arrayOf("推荐","新人","女神","附近")
    override fun initView(savedInstanceState: Bundle?) {

    }
}
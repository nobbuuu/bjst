package com.dream.bjst.identification

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.dream.bjst.R

import com.dream.bjst.databinding.ActivityIdentificationBinding
import com.dream.bjst.identification.adapter.IdentifyAdapter
import com.dream.bjst.identification.bean.identifyBean
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.main.MainActivity
import com.dream.bjst.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow

class IdentificationActivity :
    BaseActivity<IdentificationViewModel, ActivityIdentificationBinding>() {
    val mNameList: MutableList<identifyBean> = ArrayList()

    var identifyAdapter = IdentifyAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.adjustWindow(this, color = R.color.color_F8FFF0, mBinding.rootLay)
        mBinding.identifyTitle.ktClick {
            onBackPressed()
        }

        mBinding.identifyGetLoanBtn.ktClick {
            ktStartActivity(MainActivity::class)
        }
    }

    override fun initData() {
        mBinding.identifyRecyclerview.layoutManager = GridLayoutManager(this, 2)
        repeat(4) {
            mNameList.add(identifyBean(R.mipmap.identify_bank, "Authentication"))
        }
        identifyAdapter.setList(mNameList)
        mBinding.identifyRecyclerview.adapter = identifyAdapter
        identifyAdapter.setOnItemClickListener { adapter, view, position ->
            "${position}".ktToastShow()

        }


    }

    override fun initDataOnResume() {


    }

}
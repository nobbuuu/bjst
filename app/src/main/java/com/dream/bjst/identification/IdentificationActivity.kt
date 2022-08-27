package com.dream.bjst.identification

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dream.bjst.R
import com.dream.bjst.bean.EmptyBean
import com.dream.bjst.databinding.ActivityIdentificationBinding
import com.dream.bjst.identification.adapter.IdentifyAdapter
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick

class IdentificationActivity :
    BaseActivity<IdentificationViewModel, ActivityIdentificationBinding>() {
     var identifyAdapter=IdentifyAdapter()
     var mNameList=ArrayList<EmptyBean>()
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.identifyTitle.ktClick {
            onBackPressed()
        }

    }

    override fun initData() {
        mBinding.identifyRecyclerview.layoutManager=GridLayoutManager(this,2)
       repeat(4){
         mNameList.add(EmptyBean())
     }
        identifyAdapter.setList(mNameList)
    }

    override fun initDataOnResume() {



    }

}
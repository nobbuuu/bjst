package com.dream.bjst.account.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.R
import com.dream.bjst.account.vm.DeleteProgressViewModel
import com.dream.bjst.databinding.ActivityDeleteProgressBinding
import com.tcl.base.common.ui.BaseActivity

class DeleteProgressActivity : BaseActivity<DeleteProgressViewModel, ActivityDeleteProgressBinding>() {



    override fun initView(savedInstanceState: Bundle?) {
          Thread{
              for (i:Int in 10..100 step 10){
                 mBinding.deleteProgressBar.setProgress(i)
                  Thread.sleep(1000)
                  if (i==100){
                      mBinding.deteTv.text="All your date has been cleared!"
                  }
              }
          }.start()
    }

    override fun initData() {


    }

    override fun initDataOnResume() {

    }
}
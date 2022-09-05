package com.dream.bjst.account.ui

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.didichuxing.doraemonkit.util.LogUtils
import com.dream.bjst.BuildConfig
import com.dream.bjst.account.bean.AccountDeleteParam
import com.dream.bjst.account.vm.AccountViewModel
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityAccountDeleteBinding
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class AccountDeleteActivity :BaseActivity<AccountViewModel,ActivityAccountDeleteBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
    }


    override fun startObserve() {
        super.startObserve()
        viewModel.accountDeleteResult.observe(this){
             if (it.`90958095`){
                 ktStartActivity(DeleteProgressActivity::class)
             }
        }
    }

    override fun initData() {

        var param=GsonUtils.toJson(
            AccountDeleteParam(
                `958484A29186879D9B9A` = BuildConfig.VERSION_NAME,
                `978187809B999186BD90` = UserManager.getUserNo()as Int,
                `9995869F9180BD90` =10022
            )
        )
        LogUtils.dTag("paramJson",param)
        viewModel.accountDeleteData(param)


        Thread {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            ktStartActivity(DeleteProgressActivity::class)
        }.start()
        event()
    }

    override fun initDataOnResume() {

    }

    /**
     * 自定义点击方法
     */
    private fun event() {
        mBinding.accountDeleteTitle.leftView.ktClick {
            onBackPressed()
        }

        //点击文案删除数据
//        mBinding.deleteContent.ktClick {
//            ktStartActivity(DeleteProgressActivity::class)
//        }
    }
}
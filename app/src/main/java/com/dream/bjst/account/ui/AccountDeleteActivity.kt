package com.dream.bjst.account.ui

import android.content.Intent
import android.os.Bundle
import com.dream.bjst.account.vm.AccountDeleteModel
import com.dream.bjst.databinding.ActivityAccountDeleteBinding
import com.hjq.bar.TitleBar
import com.ruffian.library.widget.RTextView
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class AccountDeleteActivity :BaseActivity<AccountDeleteModel,ActivityAccountDeleteBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        Thread {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            ktStartActivity(DeleteProgressActivity::class)
            //startActivity(Intent(this@AccountDeleteActivity, DeleteProgressActivity::class.java))
        }.start()
        event()
    }

    override fun initDataOnResume() {
        TODO("Not yet implemented")
    }

    /**
     * 自定义点击方法
     */
    private fun event() {
        mBinding.accountDeleteTitle.leftView.ktClick {
            onBackPressed()
        }

        //点击文案删除数据
        mBinding.deleteContent.ktClick {
            ktStartActivity(DeleteProgressActivity::class)
        }
    }
}
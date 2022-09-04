package com.dream.bjst.account.ui

import android.content.Intent
import android.os.Bundle
import com.dream.bjst.account.vm.AccountDeleteModel
import com.dream.bjst.databinding.ActivityAccountDeleteBinding
import com.hjq.bar.TitleBar
import com.ruffian.library.widget.RTextView
import com.tcl.base.common.ui.BaseActivity

class AccountDeleteActivity :BaseActivity<AccountDeleteModel,ActivityAccountDeleteBinding>() {
    var deleteTitle: TitleBar? = null
    var deleteContent: RTextView? = null
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
            startActivity(Intent(this@AccountDeleteActivity, DeleteProgressActivity::class.java))
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
        deleteTitle!!.leftView.setOnClickListener { onBackPressed() }

        //点击文案删除数据
        deleteContent!!.setOnClickListener {
            startActivity(
                Intent(
                    this@AccountDeleteActivity,
                    DeleteProgressActivity::class.java
                )
            )
        }
    }
}
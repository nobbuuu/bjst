package com.dream.bjst.identification.ui

import android.os.Bundle
import com.dream.bjst.account.ui.AccountDeleteActivity
import com.dream.bjst.databinding.ActivityContactsApproveBinding
import com.dream.bjst.dialog.RelationDialog
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity

class ApproveContactsActivity :
    BaseActivity<IdentificationViewModel, ActivityContactsApproveBinding>() {
    lateinit var relationDialog: RelationDialog
    private var curClick = 1
    override fun initView(savedInstanceState: Bundle?) {
        relationDialog =  RelationDialog(this){
            if (curClick == 1){
                mBinding.relationLay1.setEndText(it)
            }else{
                mBinding.relationLay2.setEndText(it)
            }
        }
    }

    override fun initData() {
        event()
        mBinding.relationLay1.ktClick {
            curClick = 1
            relationDialog.show()
        }
        mBinding.relationLay2.ktClick {
            curClick = 2
            relationDialog.show()
        }
    }

    /**
     * 测试用
     */
    private fun event() {
        mBinding.sureBtn.ktClick {
            ktStartActivity(AccountDeleteActivity::class)
        }
    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()
    }

}
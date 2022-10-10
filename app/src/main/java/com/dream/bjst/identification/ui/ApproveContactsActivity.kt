package com.dream.bjst.identification.ui

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import com.blankj.utilcode.util.GsonUtils
import com.dream.bjst.account.ui.AccountDeleteActivity
import com.dream.bjst.common.UserManager
import com.dream.bjst.common.vm.DeviceInfoViewModel
import com.dream.bjst.databinding.ActivityContactsApproveBinding
import com.dream.bjst.dialog.RelationDialog
import com.dream.bjst.identification.bean.ContactsParam
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.main.MainActivity
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow

class ApproveContactsActivity :
    BaseActivity<IdentificationViewModel, ActivityContactsApproveBinding>() {
    lateinit var relationDialog: RelationDialog
    private var relationType = 0
    private var phoneType = 0
    val param = ContactsParam()
    override fun initView(savedInstanceState: Bundle?) {
        relationDialog = RelationDialog(this) { it, position ->
            if (relationType == 1) {
                mBinding.relationLay1.setEndText(it)
                param.`979B9A80959780C5A6919895809D9B9A` = position + 1 * 10
            } else {
                mBinding.relationLay2.setEndText(it)
                param.`979B9A80959780C6A6919895809D9B9A` = position + 1 * 10
            }
        }
    }

    override fun initData() {
        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }

        mBinding.relationLay1.ktClick {
            relationType = 1
            relationDialog.show()
        }
        mBinding.relationLay2.ktClick {
            relationType = 2
            relationDialog.show()
        }

        mBinding.phone1.ktClick {
            phoneType = 1
            chooseContacts()
        }
        mBinding.phone2.ktClick {
            phoneType = 2
            chooseContacts()
        }

        mBinding.sureBtn.ktClick {
            if (alreadyInput()) {
                viewModel.pushUrgencyContact(GsonUtils.toJson(param))
            }
        }
        if (UserManager.isFalseAccount()) {
            viewModel.updateDeviceInfo()
        }
    }

    fun alreadyInput(): Boolean {
        if (param.`979B9A80959780C5A6919895809D9B9A` == null) {
            "Please select your relationship with me".ktToastShow()
            return false
        }
        if (param.`979B9A80959780C6A6919895809D9B9A` == null) {
            "Please select your relationship with me".ktToastShow()
            return false
        }

        if (param.`979B9A80959780C5BA959991`.isNullOrEmpty()) {
            "Please select a contact".ktToastShow()
            return false
        }
        if (param.`979B9A80959780C6BA959991`.isNullOrEmpty()) {
            "Please select a contact".ktToastShow()
            return false
        }
        return true
    }

    private fun chooseContacts() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(intent, 333)
    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.pushContacts.observe(this) {
            if (it) {
                ktStartActivity(ApproveBankCardActivity::class)
                finish()
            } else {
                "operation failed,please try again".ktToastShow()
            }
        }
    }


    override fun onBackPressed() {
        ktStartActivity(MainActivity::class)
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                333 -> {
                    //选择通讯录联系人返回
                    data?.data?.let {
                        val cr: ContentResolver = contentResolver
                        val cursor = cr.query(
                            it,
                            arrayOf(
                                ContactsContract.CommonDataKinds.Phone.NUMBER,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                            ),
                            null,
                            null,
                            null
                        )
                        cursor?.let {
                            while (cursor.moveToNext()) {
                                val name =
                                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                val phone =
                                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                if (phoneType == 1) {
                                    param.`979B9A80959780C5BA959991` = name
                                    param.`979B9A80959780C5B99B969D9891` = phone
                                    mBinding.phone1.setEndText(phone)
                                } else {
                                    param.`979B9A80959780C6BA959991` = name
                                    param.`979B9A80959780C6B99B969D9891` = phone
                                    mBinding.phone2.setEndText(phone)
                                }
                            }
                            cursor.close()
                        }
                    }
                }
            }
        }
    }

}
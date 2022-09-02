package com.dream.bjst.identification.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.dream.bjst.R

import com.dream.bjst.databinding.ActivityIdentificationBinding
import com.dream.bjst.identification.adapter.IdentifyAdapter
import com.dream.bjst.identification.bean.IdentifyBean
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.main.MainActivity
import com.dream.bjst.utils.StatusBarUtils
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow

class ApproveMainActivity :
    BaseActivity<IdentificationViewModel, ActivityIdentificationBinding>() {
    val mApproveList: MutableList<IdentifyBean> = ArrayList()
    var identifyAdapter = IdentifyAdapter()

    val iconList = listOf(
        R.mipmap.identify_authentication,
        R.mipmap.idenfgtify_facial_verification,
        R.mipmap.emergency_contack,
        R.mipmap.identify_bank
    )
    val nameList = listOf(
        R.string.authentication,
        R.string.facial_verification,
        R.string.emergency_contact,
        R.string.bank_account
    )
    val stepList = listOf(
        listOf("10", "11", "20", "30", "31", "70"),
        listOf("40", "50", "51"),
        listOf("60"),
        listOf("80", "90")
    )

    override fun initView(savedInstanceState: Bundle?) {
        if (!(iconList.size == nameList.size && nameList.size == stepList.size)) {
            "Initialization failure".ktToastShow()
            finish()
        }
        StatusBarUtils.adjustWindow(this, color = R.color.color_F8FFF0, mBinding.rootLay)
        mBinding.identifyTitle.ktClick {
            onBackPressed()
        }

        mBinding.identifyGetLoanBtn.ktClick {
            viewModel.idCardStatus.value?.let {
                //当前进行到的项目类型,null表示都通过，10：身份证前ocr（跳转到证件ocr界面）,11:身份证后ocr（跳转到证件ocr界面）,20:身份证前ocr（跳转到证件ocr界面）,30:pan卡ocr（跳转到证件ocr界面）,31:pan号认证（跳转到证件ocr界面）,40:活体检测,50:人脸对比,51:人脸对比（跳转到活体）,60:紧急联系人认证,70:扩展信息录入,80:银行卡绑定，90：银行卡重绑定
                when (it.`9A919190B09B9D9A93BD809199`) {
                    "10", "11", "20", "30", "31" -> {
                        ktStartActivity(ApproveIdCardActivity::class)
                    }
                    "40", "50", "51" -> {
                        ktStartActivity(LivenessDetectionActivity::class)
                    }
                    "60" -> {
                        ktStartActivity(ApproveContactsActivity::class)
                    }
                    "70" -> {
                        ktStartActivity(ApproveIdCardConfirmActivity::class)
                    }
                    "80", "90" -> {
                        ktStartActivity(ApproveBankCardActivity::class)
                    }
                    else -> {
                        ktStartActivity(MainActivity::class)
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun initData() {
        mBinding.identifyRecyclerview.layoutManager = GridLayoutManager(this, 2)
        mBinding.identifyRecyclerview.adapter = identifyAdapter
        repeat(iconList.size) {
            mApproveList.add(
                IdentifyBean(
                    iconList[it],
                    StringUtils.getString(nameList[it])
                )
            )
        }
        identifyAdapter.setList(mApproveList)

    }

    override fun initDataOnResume() {
        viewModel.fetchCustomerKycStatus()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.idCardStatus.observe(this) {
            it.`9A919190B09B9D9A93BD809199`?.let {
                var tempIndex = 0
                stepList.forEachIndexed { index, list ->
                    if (list.contains(it)) {
                        tempIndex = index
                    }
                }
                mApproveList.clear()
                repeat(iconList.size) { index ->
                    mApproveList.add(
                        IdentifyBean(
                            iconList[index],
                            StringUtils.getString(nameList[index]),
                            isApproved = index < tempIndex
                        )
                    )
                }
                identifyAdapter.setList(mApproveList)
            }
        }
    }
}
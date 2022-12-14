package com.kredit.cash.loan.app.identification.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.kredit.cash.loan.app.R

import com.kredit.cash.loan.app.databinding.ActivityIdentificationBinding
import com.kredit.cash.loan.app.identification.adapter.IdentifyAdapter
import com.kredit.cash.loan.app.identification.bean.IdentifyBean
import com.kredit.cash.loan.app.identification.vm.IdentificationViewModel
import com.kredit.cash.loan.app.main.MainActivity
import com.kredit.cash.loan.app.utils.StatusBarUtils
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
                //??????????????????????????????,null??????????????????10???????????????ocr??????????????????ocr?????????,11:????????????ocr??????????????????ocr?????????,20:????????????ocr??????????????????ocr?????????,30:pan???ocr??????????????????ocr?????????,31:pan???????????????????????????ocr?????????,40:????????????,50:????????????,51:?????????????????????????????????,60:?????????????????????,70:??????????????????,80:??????????????????90?????????????????????
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
        viewModel.fetchHomeInfo()
    }

    override fun initDataOnResume() {
        viewModel.fetchCustomerKycStatus()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.homeData.observe(this) {
            mBinding.loanAmount.text = "??? " + it.`989B959AB5999B819A80`
            when (it.`8491869B9D90A19A9D80`) {
                10 -> {//???
                    mBinding.days.text = it.`8491869B9D90`.toString() + " Days"
                }
                20 -> {//???
                    mBinding.days.text = it.`8491869B9D90`.toString() + " months"
                }
            }
            mBinding.interestRateTv.text = it.`999B9A809C87A6958091` + "%"
        }
        viewModel.idCardStatus.observe(this) {
            var isAll = it.`959898BD809199A4958787`
            //????????????????????????????????????????????????
            if (isAll) {
                mBinding.interestItem.visibility = View.GONE
                mBinding.loanAmountItem.visibility = View.GONE
                mBinding.loanPeriodItem.visibility = View.GONE
                mBinding.identifyGetLoanBtn.visibility = View.GONE
            }
            var tempIndex = 0
            stepList.forEachIndexed { index, list ->
                if (list.contains(it.`9A919190B09B9D9A93BD809199`)) {
                    tempIndex = index
                }
            }
            mApproveList.clear()
            repeat(iconList.size) { index ->
                mApproveList.add(
                    IdentifyBean(
                        iconList[index],
                        StringUtils.getString(nameList[index]),
                        isApproved = if (isAll) true else index < tempIndex
                    )
                )
            }
            identifyAdapter.setList(mApproveList)
        }
    }
}
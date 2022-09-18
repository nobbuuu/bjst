package com.dream.bjst.loan.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.dream.bjst.R
import com.dream.bjst.account.ui.DeleteProgressActivity
import com.dream.bjst.databinding.FragmentLoanBinding
import com.dream.bjst.dialog.AmountDialog
import com.dream.bjst.dialog.LoanInfoDialog
import com.dream.bjst.dialog.UnLoanDialog
import com.dream.bjst.loan.adapter.LoanProductAdapter
import com.dream.bjst.loan.bean.AmountPeriodBean
import com.dream.bjst.loan.bean.ApplyListParamBean
import com.dream.bjst.loan.bean.ApplyParamBean
import com.dream.bjst.loan.bean.LoanConfirmBean
import com.dream.bjst.loan.vm.LoanViewModel
import com.dream.bjst.utils.DeviceUtils
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow
import com.tcl.base.kt.nullToEmpty
import com.tcl.base.weiget.recylerview.RecycleViewDivider
import rxhttp.wrapper.utils.GsonUtil
import java.text.SimpleDateFormat
import java.util.*

class LoanFragment : BaseFragment<LoanViewModel, FragmentLoanBinding>() {

    lateinit var loanAdapter: LoanProductAdapter
    lateinit var mAmountDialog: AmountDialog
    lateinit var mPeriodDialog: AmountDialog
    private val amountList = arrayListOf<AmountPeriodBean>()
    private val periodList = arrayListOf<AmountPeriodBean>()
    private val mHandler = Handler(Looper.getMainLooper())
    private var countDown: Long = 3 * 60 * (1000L)
    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.userIcon)
        loanAdapter = LoanProductAdapter()
        mAmountDialog = AmountDialog(requireContext())
        mPeriodDialog = AmountDialog(requireContext(), type = 2)
        mBinding.otherLoanRv.apply {
            adapter = loanAdapter
            addItemDecoration(RecycleViewDivider(requireContext(), LinearLayoutManager.VERTICAL))
        }

        viewModel.fetchProducts()
        onEvent()
    }

    override fun onStart() {
        super.onStart()
        mHandler.postDelayed(mRunnable, 1000)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mRunnable)
    }

    fun onEvent() {
        mBinding.ordersLay.ktClick {
            ktStartActivity(LoanRecordsActivity::class)
        }
        mBinding.amountLay.ktClick {
            mAmountDialog.setData(amountList).show()
        }
        mBinding.periodLay.ktClick {
            mPeriodDialog.setData(periodList).show()
        }
        //选择贷款金额后刷新产品列表
        mAmountDialog.setOnSelectListener {
            mBinding.amountTv.text = "₹ " + it.num
            var tempAmount = 0
            var amountReceive = 0
            var repayAmount = 0
            loanAdapter.data.forEachIndexed { index, bean ->
                tempAmount += bean.`989B959AB5999B819A80`
                bean.isCheck = tempAmount <= it.num
                if (bean.isCheck) {
                    amountReceive += bean.`869197919D8291B5999B819A80`
                    repayAmount += bean.`9A919190A69184958DB5999B819A80`
                }
            }
            loanAdapter.notifyDataSetChanged()
            mBinding.amountReceiveNum.text = "₹ $amountReceive"
            mBinding.repayAmount.text = "₹ $repayAmount"
        }
        mPeriodDialog.setOnSelectListener {
            mBinding.amountTv.text = it.num.toString() + " Days"
        }
        mBinding.loanNow.ktClick {

            if (!mBinding.unLoanLay.isVisible) {
                viewModel.fetchCustomerKycStatus()
            } else {
                viewModel.loanPreData.value?.let {
                    if (it.`83959D80A69184958DB5999B819A80` <= 0) {
                        UnLoanDialog(requireContext()) {
                            Navigation.findNavController(mBinding.loanRoot)
                                .navigate(R.id.loan_to_navigation_repayment)
                        }.show()
                    } else if (it.`978691909D80B5999B819A80` <= 0) {
                        "No application product available".ktToastShow()
                    }
                }
            }
        }
    }

    private val mRunnable = object : Runnable {
        override fun run() {
            if (countDown > 0) {
                countDown -= 1000
                mBinding.countDownTv.text =
                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(Date(countDown))
                mHandler.removeCallbacks(this)
                mHandler.postDelayed(this, 1000)
            } else {
                countDown = 0
                mBinding.countDownTv.text = "00:00"
                mHandler.removeCallbacks(this)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.userStatus.observe(this) {

            //是否黑名单
            if (!it.`969895979F`) {
                val applyProducts: MutableList<ApplyListParamBean> = arrayListOf()
                loanAdapter.data.filter { it.isCheck }?.forEach {
                    applyProducts.add(
                        ApplyListParamBean(
                            it.`9D90`.toString(),
                            it.`989B959AB5999B819A80`.toString()
                        )
                    )
                }
                val param = GsonUtil.toJson(
                    ApplyParamBean(
                        `9D84` = DeviceUtils.getLocalIPAddress(),
                        `84869B9081978087` = applyProducts
                    )
                )
                viewModel.apply(param)
            } else {
                UnLoanDialog(requireContext(), type = 2) {
                    ktStartActivity(DeleteProgressActivity::class)
                }.show()
            }
        }
        viewModel.applyData.observe(this) {
            viewModel.fetchProducts()
            if (!it.`90809BB69B86869B83B58484988DA6918781988087`.isNullOrEmpty()) {
                val loanAmount = mBinding.amountTv.text.toString()
                val repayAmount = mBinding.repayAmount.text.toString()
                val bean = LoanConfirmBean(
                    loanAmount,
                    repayAmount,
                    it.`908191B0958091`.nullToEmpty(),
                    it.`869187B29180979CA4869B90819780`.`84869B90819780B89D8780`
                )
                LoanInfoDialog(requireContext(), bean) {
                    ktStartActivity(LoanRecordsActivity::class)
                }.show()
            }
        }
        viewModel.loanPreData.observe(this) {
            val unLoan =
                it.`978691909D80B5999B819A80` <= 0 || it.`83959D80A69184958DB5999B819A80` <= 0
            mBinding.unLoanLay.isVisible = unLoan
            if (it.`83959D80A69184958DB5999B819A80` <= 0) {
                mBinding.unLoanSummary.text = "Recovery amount after repayment"
            } else if (it.`978691909D80B5999B819A80` <= 0) {
                mBinding.unLoanSummary.text = "No application product available"
            }
        }
        viewModel.loanData.observe(this) {

            //刷新Amount弹框数据和产品列表数据
            val defaultChooseNum = it.`90919295819880A79198919780A4869B90819780B79B819A80`
            it.`84869B90819780B89D8780`?.forEachIndexed { index, bean ->
                bean.isCheck = index < defaultChooseNum
            }
            loanAdapter.setList(it.`84869B90819780B89D8780`)

            var amount = 0
            var amountReceive = 0
            var repayAmount = 0
            amountList.clear()

            loanAdapter.data.forEachIndexed { index, bean ->
                amount += bean.`989B959AB5999B819A80`
                amountList.add(
                    AmountPeriodBean(
                        num = amount,
                        isEnable = index < it.`99958CB89B959AA4869B90819780B79B819A80`,
                        isCheck = defaultChooseNum == index + 1
                    )
                )
                if (index < defaultChooseNum) {
                    amountReceive += bean.`869197919D8291B5999B819A80`
                    repayAmount += bean.`9A919190A69184958DB5999B819A80`
                }
            }
            if (amountList.size >= defaultChooseNum) {
                mBinding.amountTv.text = "₹ " + amountList[defaultChooseNum - 1].num
                mBinding.amountReceiveNum.text = "₹ $amountReceive"
                mBinding.repayAmount.text = "₹ $repayAmount"
                mBinding.repaymentDate.text = it.`869184958DB0958091`
            }

            //借款周期
            periodList.clear()
            it.`989B959AA6959A939187`.forEachIndexed { index, bean ->
                periodList.add(
                    AmountPeriodBean(
                        num = bean.`989B959AA6959A9391`,
                        isEnable = bean.`919A95969891`,
                        isCheck = index == 0
                    )
                )
            }
            if (periodList.isNotEmpty()) {
                mBinding.days.text = periodList[0].num.toString() + " Days"
            }
            mBinding.fkAccountLay.isVisible = it.`929FB597979B819A80`
        }
    }

}
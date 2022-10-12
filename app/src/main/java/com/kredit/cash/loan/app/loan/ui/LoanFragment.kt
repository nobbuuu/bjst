package com.kredit.cash.loan.app.loan.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.account.ui.DeleteProgressActivity
import com.kredit.cash.loan.app.databinding.FragmentLoanBinding
import com.kredit.cash.loan.app.dialog.AmountPeriodDialog
import com.kredit.cash.loan.app.dialog.LoanInfoDialog
import com.kredit.cash.loan.app.dialog.UnLoanDialog
import com.kredit.cash.loan.app.loan.adapter.LoanProductAdapter
import com.kredit.cash.loan.app.loan.bean.AmountPeriodBean
import com.kredit.cash.loan.app.loan.bean.ApplyListParamBean
import com.kredit.cash.loan.app.loan.bean.ApplyParamBean
import com.kredit.cash.loan.app.loan.bean.LoanConfirmBean
import com.kredit.cash.loan.app.loan.vm.LoanViewModel
import com.tcl.base.common.ui.BaseFragment
import com.tcl.base.kt.*
import com.tcl.base.weiget.recylerview.RecycleViewDivider
import rxhttp.wrapper.utils.GsonUtil
import java.text.SimpleDateFormat
import java.util.*


class LoanFragment : BaseFragment<LoanViewModel, FragmentLoanBinding>() {

    lateinit var loanAdapter: LoanProductAdapter
    lateinit var mAmountPeriodDialog: AmountPeriodDialog
    lateinit var mPeriodDialog: AmountPeriodDialog
    private val amountList = arrayListOf<AmountPeriodBean>()
    private val periodList = arrayListOf<AmountPeriodBean>()
    private val mHandler = Handler(Looper.getMainLooper())
    private var countDown: Long = 3 * 60 * (1000L)
    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.userIcon)
        loanAdapter = LoanProductAdapter()
        mAmountPeriodDialog = AmountPeriodDialog(requireContext())
        mPeriodDialog = AmountPeriodDialog(requireContext(), type = 2)
        mBinding.otherLoanRv.apply {
            adapter = loanAdapter
            addItemDecoration(RecycleViewDivider(requireContext(), LinearLayoutManager.VERTICAL))
        }

        loadData()
        onEvent()
        viewModel.repaymentData()
    }

    private fun loadData() {
        viewModel.fetchCreditAmount()
        viewModel.fetchProducts()
        viewModel.fetchProcessingOrderCount()
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        mHandler.postDelayed(mRunnable, 1000)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mRunnable)
    }

    fun onEvent() {
        mBinding.smartRefresh.setOnRefreshListener {
            loadData()
        }
        mBinding.ordersLay.ktClick {
            ktStartActivity4Result(LoanRecordsActivity::class, 920)
        }
        mBinding.amountLay.ktClick {
            mAmountPeriodDialog.setData(amountList).show()
        }
        mBinding.periodLay.ktClick {
            mPeriodDialog.setData(periodList).show()
        }
        //选择贷款金额后刷新产品列表
        mAmountPeriodDialog.setOnSelectListener {
            mBinding.amountTv.text = "₹ " + it.num
            var tempAmount = 0
            var amountReceive = 0
            var repayAmount = 0
            loanAdapter.data.forEachIndexed { index, bean ->
                tempAmount += bean.`989B959AB5999B819A80`
                bean.isCheck = tempAmount <= it.num.toDouble()
                if (bean.isCheck) {
                    amountReceive += bean.`869197919D8291B5999B819A80`
                    repayAmount += bean.`9A919190A69184958DB5999B819A80`
                }
            }
            loanAdapter.notifyDataSetChanged()
            mBinding.amountReceiveNum.text = "₹ $amountReceive"
            mBinding.repayAmount.text = "₹ $repayAmount"
            refreshFk()
        }
        mPeriodDialog.setOnSelectListener {
            mBinding.days.text = it.num + " Days"
            refreshFk()
        }
        mBinding.loanNow.ktClick {

            if (!mBinding.unLoanLay.isVisible) {
                viewModel.fetchCustomerKycStatus()
            } else {
                viewModel.loanPreData.value?.let {
                    if (it.`83959D80A69184958DB5999B819A80` > 0) {
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
        viewModel.repaymentResult.observe(this) { repayBean ->
            //存在待还款订单
            if (!repayBean.`908191A09B90958DBB8690918687`.isNullOrEmpty() || !repayBean.`9A9B80B08191BB8690918687`.isNullOrEmpty() || !repayBean.`9B829186908191BB8690918687`.isNullOrEmpty()) {
                val bundle = bundleOf()
                bundle.putSerializable("reData", repayBean)
                Navigation.findNavController(mBinding.smartRefresh)
                    .navigate(R.id.loan_to_navigation_repayment, bundle)
            }
        }
        viewModel.refreshResult.observe(this) {
            mBinding.smartRefresh.finishRefresh()
        }
        viewModel.upDeviceInfo.observe(this) {
            mBinding.smartRefresh.finishRefresh()
        }
        viewModel.processOrders.observe(this) {//处理中的订单数量
            mBinding.processNumTv.text = "$it orders processing currently"
            try {
                mBinding.ordersLay.isVisible = it.toInt() > 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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
                        `9D84` = com.kredit.cash.loan.app.utils.DeviceUtils.getGlobalIPAddress(),
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
            if (!it.`90809BB69B86869B83B58484988DA6918781988087`.isNullOrEmpty()) {//申请成功
                viewModel.updateDeviceInfo()
                val loanAmount = mBinding.amountTv.text.toString()
                val repayAmount = mBinding.repayAmount.text.toString()
                val bean = LoanConfirmBean(
                    loanAmount,
                    repayAmount,
                    mBinding.repaymentDate.text.toString(),
                    loanAdapter.data.filter { it.isCheck }
                )
                LoanInfoDialog(requireContext(), bean) {
                    ktStartActivity4Result(LoanRecordsActivity::class, 920)
                }.show()
            }
            viewModel.fetchProducts()
        }
        viewModel.loanPreData.observe(this) {
            /*val unLoan =
                it.`978691909D80B5999B819A80` <= 0 || it.`83959D80A69184958DB5999B819A80` > 0
            mBinding.unLoanLay.isVisible = unLoan
            if (it.`83959D80A69184958DB5999B819A80` > 0) {
                mBinding.unLoanSummary.text = "Recovery amount after repayment"
            } else if (it.`978691909D80B5999B819A80` <= 0) {
                mBinding.unLoanSummary.text = "No application product available"
            }*/
        }
        viewModel.loanData.observe(this) {
            mBinding.smartRefresh.finishRefresh()
            mBinding.unLoanLay.isVisible = it.`99958CB89B959AA4869B90819780B79B819A80` <= 0

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
                val canLoanProduct = index < it.`99958CB89B959AA4869B90819780B79B819A80`
                if (canLoanProduct) {
                    amount += bean.`989B959AB5999B819A80`
                    amountList.add(
                        AmountPeriodBean(
                            num = amount.toString(),
                            isEnable = canLoanProduct,
                            isCheck = defaultChooseNum == index + 1
                        )
                    )
                }

                if (index < defaultChooseNum) {
                    amountReceive += bean.`869197919D8291B5999B819A80`
                    repayAmount += bean.`9A919190A69184958DB5999B819A80`
                }
            }

            if (amountList.isNotEmpty()) {
                amountList.add(AmountPeriodBean(num = amountList.last().num.bigDecimalPrice("1.2")))
            } else {
                amountList.add(AmountPeriodBean(num = "0"))
            }
            amountList.add(AmountPeriodBean(num = "300000"))

            if (amountList.size - 2 >= defaultChooseNum) {
                mBinding.amountTv.text = "₹ " + amountList[defaultChooseNum - 1].num
                mBinding.amountReceiveNum.text = "₹ $amountReceive"
                mBinding.repayAmount.text = "₹ $repayAmount"
            }
            mBinding.repaymentDate.text = it.`869184958DB0958091`


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
            mBinding.days.text = periodList.getOrNull(0)?.num + " Days"
            mBinding.fkAccountLay.isVisible = it.`929FB597979B819A80`
            refreshFk()
        }
    }

    fun refreshFk() {
        val months = mBinding.days.text.toString().split(" ")[0].bigDecimalDivide("30")
        val loanAmount = mBinding.amountTv.text.toString().split(" ")[1]
        val pa = loanAmount.bigDecimalDivide(months)
        val tempAmount = loanAmount.bigDecimalPrice("0.02").bigDecimalPlus(pa)
        mBinding.accrualTv.text =
            "Monthly repayment ₹ $tempAmount, ₹ $tempAmount=loan mount*2%+loan amount/loan months"
    }

}
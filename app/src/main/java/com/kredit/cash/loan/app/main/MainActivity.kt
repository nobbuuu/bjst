package com.kredit.cash.loan.app.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.didichuxing.doraemonkit.util.LocationUtils
import com.kredit.cash.loan.app.BuildConfig
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.account.ui.DeleteProgressActivity
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.common.UserManager
import com.kredit.cash.loan.app.databinding.ActivityMainBinding
import com.kredit.cash.loan.app.dialog.UnLoanDialog
import com.kredit.cash.loan.app.main.menu.*
import com.kredit.cash.loan.app.main.vm.MainViewModel
import com.kredit.cash.loan.app.receiver.BatteryReceiver
import com.kredit.cash.loan.app.upgrade.NewVersionBean
import com.kredit.cash.loan.app.upgrade.UpgradeDialogFragment
import com.google.android.material.tabs.TabLayout
import com.ruffian.library.widget.RTextView
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.download.DownloadApkBetterHelper
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.nullToEmpty
import com.tcl.base.utils.MmkvUtil
import com.tcl.tclzjpro.main.FixFragmentNavigator

/**
 *@author tiaozi
 *@date   2021/12/8
 *description
 */
var homeType = Constant.ACTION_TYPE_HOME

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    var lastPos = -1
    var curPos = MAIN_TAB_LOAN
    private lateinit var controller: NavController
    private var batteryReceiver: BatteryReceiver? = null

    init {
        config.isDoubleBack = true
    }

    override fun initView(savedInstanceState: Bundle?) {
        initLocation()
        initReceiver()
        initBotNav()
        onEvent()
        val isUpDeviceInfo = intent.getBooleanExtra("isUpDeviceInfo", false)
        if (isUpDeviceInfo) {
            viewModel.uploadDeviceLocation()
            viewModel.updateDeviceInfo()
        }
    }

    private fun initBotNav() {
        homeType = intent.getIntExtra(Constant.actionType, homeType)
        controller = findNavController(R.id.main_container)
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navigator = FixFragmentNavigator(this, supportFragmentManager, fragment.id)
        controller.navigatorProvider.addNavigator(navigator)
        val navInflater = controller.navInflater
        val navGraph = navInflater.inflate(R.navigation.main_navigation)
        if (homeType == Constant.ACTION_TYPE_MAIN) {
            navGraph.startDestination = R.id.navigation_loan
        } else {
            navGraph.startDestination = R.id.navigation_home
        }
        controller.graph = navGraph

        repeat(TabManager.menus.size) {
            val tab = mBinding.mainTab.newTab()
            tab.customView = MainTabView(this, TabManager.menus[it])
            mBinding.mainTab.addTab(tab)
        }
    }

    private fun onEvent() {
        mBinding.mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.run {
                    if (curPos != position) {
                        lastPos = curPos
                        curPos = position
                        switchTab(curPos)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //No-op
            }

            override fun onTabReselected(tab: TabLayout.Tab?) { // No-op
            }
        })
        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.color_F8FFF0))
                    curPos = MAIN_TAB_LOAN
                }
                R.id.navigation_loan -> {
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.white))
                    curPos = MAIN_TAB_LOAN
                }
                R.id.navigation_repayment -> {
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.white))
                    curPos = MAIN_TAB_REPAYMENT
                }
                R.id.navigation_account -> {
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.color_F8FFF0))
                    curPos = MAIN_TAB_ACCOUNT
                }
            }
            mBinding.mainTab.selectTab(mBinding.mainTab.getTabAt(curPos))
        }
    }

    @SuppressLint("MissingPermission")
    fun initLocation() {
        LocationUtils.register(0, 0, object : LocationUtils.OnLocationChangeListener {
            override fun getLastKnownLocation(location: Location?) {
                LogUtils.dTag("locationTag", "getLastKnownLocation----------")
                location?.let {
                    LogUtils.dTag(
                        "locationTag",
                        LocationUtils.getLocality(it.latitude, it.longitude)
                    )
                    MmkvUtil.encode("curLatitude", it.latitude)
                    MmkvUtil.encode("curLongitude", it.longitude)
                }
            }

            override fun onLocationChanged(location: Location?) {
                location?.let {
                    LogUtils.dTag(
                        "locationTag",
                        LocationUtils.getLocality(it.latitude, it.longitude)
                    )
                    MmkvUtil.encode("curLatitude", it.latitude)
                    MmkvUtil.encode("curLongitude", it.longitude)
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            }
        })
    }

    private fun initReceiver() {
        batteryReceiver = BatteryReceiver()
        val batteryFilter = IntentFilter()
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, batteryFilter)
    }

    override fun onBlockBackPressed(): Boolean {
        return curPos != MAIN_TAB_LOAN
    }

    override fun doOnBlockBackPressed() {
        super.doOnBlockBackPressed()
        findNavController(R.id.main_container).navigate(R.id.navigation_loan)
    }

    /**????????????????????????*/
    private fun switchTab(curPos: Int) {
        when (curPos) {
            MAIN_TAB_LOAN -> {
                if (homeType == Constant.ACTION_TYPE_MAIN) {
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.white))
                    controller.navigate(R.id.navigation_loan)
                } else {
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.color_F8FFF0))
                    controller.navigate(R.id.navigation_home)
                }
            }
            MAIN_TAB_REPAYMENT -> {
                BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.white))
                controller.navigate(R.id.navigation_repayment)
            }
            MAIN_TAB_ACCOUNT -> {
                BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.color_F8FFF0))
                controller.navigate(R.id.navigation_account)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        /**
         * ????????????
         */
        viewModel.upGradeResults.observe(this) {
            if (it.`97818686919A80A29186879D9B9A` > BuildConfig.VERSION_CODE.toString()) {
                it.`869187B58484A18490958091A08C80BD9A929B`?.let { data ->
                    val bean = NewVersionBean(
                        versionNumber = it.`97818686919A80A29186879D9B9A`,
                        url = it.`97818686919A80A18698`,
                        content = it.`869187B58484A18490958091A08C80BD9A929B`.`808C80BD809199`,
                        remarks = it.`869187B58484A18490958091A08C80BD9A929B`.`809D809891`,
                        force = it.`99959A9095809B868DA18490958091`
                    )
                    doUpgrade(bean)
                }
            }
        }

        viewModel.userStatus.observe(this) {
            //???????????????
            if (it.`969895979F`) {
                UnLoanDialog(this, type = 2) {
                    ktStartActivity(DeleteProgressActivity::class)
                }.show()
            }
            if (it.`9A919190B09B9D9A93BD809199` == "90") {//??????????????????
                val lastTab = mBinding.mainTab.getTabAt(TabManager.menus.lastIndex)
                if (lastTab?.customView is MainTabView) {
                    lastTab?.customView?.findViewById<RTextView>(R.id.tabUnreadCount)?.isVisible =
                        true
                }
            }
        }
    }

    lateinit var upgradeDialogFragment: UpgradeDialogFragment
    private fun doUpgrade(newVersionBean: NewVersionBean) {
        upgradeDialogFragment = UpgradeDialogFragment.newInstance(newVersionBean)
        DownloadApkBetterHelper.initConfig(
            versionName = newVersionBean.versionNumber.nullToEmpty(),
            curDownLoadUrl = newVersionBean.url.nullToEmpty(),
            curFileName = "luckRupee-${newVersionBean.versionNumber}.apk",
            iUpgradeListener = upgradeDialogFragment,
            isForce = newVersionBean.force

        )
        upgradeDialogFragment.show(supportFragmentManager, UpgradeDialogFragment::class.java.name)
    }

    @SuppressLint("MissingPermission")
    override fun initData() {
        viewModel.fetchCustomerKycStatus()
    }

    override fun initDataOnResume() {
        //????????????
        if (!UserManager.isFalseAccount()) {
            viewModel.upGradeContent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            300 -> {
                controller.navigate(R.id.navigation_repayment)
            }
            400, 500 -> {
                controller.navigate(R.id.navigation_loan)
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(batteryReceiver)
        super.onDestroy()
    }
}
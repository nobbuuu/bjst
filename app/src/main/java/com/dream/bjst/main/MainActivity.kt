package com.dream.bjst.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.BuildConfig
import com.dream.bjst.R
import com.dream.bjst.account.ui.DeleteProgressActivity
import com.dream.bjst.common.Constant
import com.dream.bjst.common.UserManager
import com.dream.bjst.databinding.ActivityMainBinding
import com.dream.bjst.dialog.UnLoanDialog
import com.dream.bjst.main.menu.*
import com.dream.bjst.main.vm.MainViewModel
import com.dream.bjst.upgrade.NewVersionBean
import com.dream.bjst.upgrade.UpgradeDialogFragment
import com.google.android.material.tabs.TabLayout
import com.ruffian.library.widget.RTextView
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.download.DownloadApkBetterHelper
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.nullToEmpty
import com.tcl.tclzjpro.main.FixFragmentNavigator

/**
 *@author tiaozi
 *@date   2021/12/8
 *description
 */
var homeType = Constant.ACTION_TYPE_MAIN

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    var lastPos = -1
    var curPos = MAIN_TAB_LOAN
    private lateinit var controller: NavController

    init {
        config.isDoubleBack = true
    }

    override fun initView(savedInstanceState: Bundle?) {
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
                R.id.navigation_loan -> {
                    curPos = MAIN_TAB_LOAN
                }
                R.id.navigation_repayment -> {
                    curPos = MAIN_TAB_REPAYMENT
                }
                R.id.navigation_account -> {
                    curPos = MAIN_TAB_ACCOUNT
                }
            }
            mBinding.mainTab.selectTab(mBinding.mainTab.getTabAt(curPos))
        }
    }

    override fun onBlockBackPressed(): Boolean {
        return curPos != MAIN_TAB_LOAN
    }

    override fun doOnBlockBackPressed() {
        super.doOnBlockBackPressed()
        findNavController(R.id.main_container).navigate(R.id.navigation_loan)
    }

    /**根据下标切换页面*/
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
         * 版本更新
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
            //是否黑名单
            if (it.`969895979F`) {
                UnLoanDialog(this, type = 2) {
                    ktStartActivity(DeleteProgressActivity::class)
                }.show()
            }
            if (it.`9A919190B09B9D9A93BD809199` == "90") {//银行卡重绑定
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
        if (UserManager.isLogin()) {
            viewModel.fetchCustomerKycStatus()
        }
    }

    override fun initDataOnResume() {
        //版本更新
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
}
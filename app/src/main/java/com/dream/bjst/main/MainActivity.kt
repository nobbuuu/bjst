package com.dream.bjst.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.dream.bjst.BuildConfig
import com.dream.bjst.R
import com.dream.bjst.databinding.ActivityMainBinding
import com.dream.bjst.main.menu.*
import com.dream.bjst.main.vm.MainViewModel
import com.dream.bjst.upgrade.NewVersionBean
import com.dream.bjst.upgrade.UpgradeDialogFragment
import com.google.android.material.tabs.TabLayout
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.download.DownloadApkBetterHelper
import com.tcl.base.kt.nullToEmpty
import com.tcl.tclzjpro.main.FixFragmentNavigator

/**
 *@author tiaozi
 *@date   2021/12/8
 *description
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    var lastPos = -1
    var curPos = MAIN_TAB_LOAN
    private lateinit var controller: NavController

    init {
        config.isDoubleBack = true
    }

    override fun initStateBar(stateBarColor: Int, isLightMode: Boolean, fakeView: View?) {
        super.initStateBar(ColorUtils.getColor(R.color.white), false, fakeView)
    }

    override fun initView(savedInstanceState: Bundle?) {

        controller = findNavController(R.id.main_container)
        val fragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navigator = FixFragmentNavigator(this, supportFragmentManager, fragment.id)
        controller.navigatorProvider.addNavigator(navigator)
        controller.setGraph(R.navigation.main_navigation)

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
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.white))
                }
                R.id.navigation_repayment -> {
                    curPos = MAIN_TAB_REPAYMENT
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.white))
                }
                R.id.navigation_account -> {
                    curPos = MAIN_TAB_ACCOUNT
                    BarUtils.setStatusBarColor(this, ColorUtils.getColor(R.color.color_F8FFF0))
                }
            }
            mBinding.mainTab.selectTab(mBinding.mainTab.getTabAt(curPos))
        }
    }

    override fun onBlockBackPressed(): Boolean {
        return curPos != MAIN_TAB_REPAYMENT
    }

    override fun doOnBlockBackPressed() {
        super.doOnBlockBackPressed()
        findNavController(R.id.main_container).navigate(R.id.navigation_repayment)
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.main_container).navigate(R.id.navigation_repayment)
    }

    /**监听新的intent*/
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.run {
            val position = getIntExtra(KEY_TAB_POSITION, 0)
            switchTab(position)
        }
    }

    /**根据下标切换页面*/
    private fun switchTab(curPos: Int) {
        when (curPos) {
            MAIN_TAB_LOAN -> controller.navigate(R.id.navigation_loan)
            MAIN_TAB_REPAYMENT -> controller.navigate(R.id.navigation_repayment)
            MAIN_TAB_ACCOUNT -> controller.navigate(R.id.navigation_account)
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
                        remarks = it.`869187B58484A18490958091A08C80BD9A929B`.`809D809891`

                    )
                    doUpgrade(bean)
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
            iUpgradeListener = upgradeDialogFragment
        )
        upgradeDialogFragment.show(supportFragmentManager, UpgradeDialogFragment::class.java.name)
    }

    override fun initData() {
        //版本更新
        viewModel.upGradeContent()
    }

    override fun initDataOnResume() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 921) {
            controller.navigate(R.id.navigation_repayment)
        }
    }
}
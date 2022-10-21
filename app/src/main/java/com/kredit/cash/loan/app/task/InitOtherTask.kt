package com.kredit.cash.loan.app.task

import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.hjq.bar.TitleBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kredit.cash.loan.app.BuildConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tcl.launcher.task.Task

/**
 * @author : tz
 * time : 2020/11/10 13:55
 */
class InitOtherTask : Task() {
    override fun run() {
        LogUtils.getConfig().let {
            val debugMode = BuildConfig.DEBUG
            it.globalTag = "TsalesTag"
            it.setConsoleFilter(if (debugMode) LogUtils.V else LogUtils.W)
            it.isLog2FileSwitch = true
        }
        LiveEventBus.config().lifecycleObserverAlwaysActive(true).enableLogger(BuildConfig.DEBUG)
        initSmartRefresh()
        initTitleBar()
        initAdjust()
    }

    private fun initAdjust(){
        val appToken = "iga6r97vwr28"
        val environment: String = AdjustConfig.ENVIRONMENT_PRODUCTION
        val config = AdjustConfig(Utils.getApp(), appToken, environment)
        Adjust.onCreate(config)
    }

    private fun initSmartRefresh() {
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            //开启越界拖动
            layout.setEnableOverScrollDrag(true)
            layout.setEnableHeaderTranslationContent(true)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(
                context
            )
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(
                context
            )
        }
    }

    private fun initTitleBar() {
        TitleBar.setDefaultStyle(com.kredit.cash.loan.app.base.TclTitleBarStyle())
    }
}
//package com.dream.bjst.dialog
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import android.view.Gravity
//import android.view.View
//import androidx.annotation.RequiresApi
//import androidx.core.app.ActivityCompat.startActivityForResult
//import androidx.lifecycle.lifecycleScope
//import com.blankj.utilcode.util.ToastUtils
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.viewholder.BaseViewHolder
//import com.didichuxing.doraemonkit.kit.core.DokitServiceEnum
//import com.dream.bjst.BuildConfig
//import com.dream.bjst.R
//import com.dream.bjst.databinding.DialogNoticeUpgradeBinding
//import com.dream.bjst.bean.ResAppUpdateTxtInfo
//import com.tcl.base.download.DownloadApkBetterHelper
//import com.tcl.base.download.IUpgradeListener
//import com.tcl.base.download.InstallApkHelper
//import com.tcl.base.kt.ktClick
//import com.tcl.base.kt.nullToEmpty
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import java.io.File
//import kotlin.concurrent.thread
//
///**
// * 创建日期：2022-09-16 on 10:48
// * 描述:衣带渐宽终不悔、为伊消得人憔悴
// * 作者:HeGuiCun Administrator
// */
//class UpgradeNoticeDialog(
//    context: Context,
//    val data: ResAppUpdateTxtInfo,
//    val sureListener: (() -> Unit)? = null
//) :
//    BaseBindingDialog<DialogNoticeUpgradeBinding>(context, gravity = Gravity.CENTER),
//    IUpgradeListener {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        event()
//        mBinding.upgradeTitle.text = data.`809D809891`
//        val contentAdapter = ContentAdapter()
//        mBinding.updateContentRv.apply {
//            adapter = contentAdapter
//        }
//        contentAdapter.setList(data.`808C80BD809199`)
//    }
//
//    private fun event() {
//        takeIf {!data.`99959A9095809B868DA18490958091`}?.let {mBinding.noticeFinishImage.ktClick {
//            dismiss()
//            sureListener?.invoke()
//        }}
//
//        mBinding.sureUpdateNowBtn.ktClick {
//            mBinding.upgradeNoticeLL.visibility = View.GONE
//            mBinding.noticeFinishImage.visibility = View.GONE
//            mBinding.upgradeProgress.visibility = View.VISIBLE
//            //下载apk
////            lateinit var upgradeNoticeDialog:UpgradeNoticeDialog
//           DownloadApkBetterHelper.initConfig(
//               versionName = data.`809D809891`.nullToEmpty(),
//               curDownLoadUrl = data.`97818686919A80A18698`.nullToEmpty(),
//               curFileName = "luckyRupee.apk",
//               iUpgradeListener = this
//           )
//            onUpdate(100)
//        }
//    }
//
//    inner class ContentAdapter :
//        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_update_content) {
//        override fun convert(holder: BaseViewHolder, item: String) {
//            holder.setText(R.id.upgradeListTv, item)
//        }
//    }
//
//    override fun onUpdate(progress: Int) {
////        mBinding.tvDownloadingTip.text = "$progress%"
//        mBinding.upgradeProgressBar.progress = progress
//        if (progress == 100) {
//            showInstallMode()
//        }
//    }
//
//    override fun onError() {
//        showRetryMode()
//    }
//    /**重试模式*/
//    private fun showRetryMode() {
//        //异常在子线程回调
//        DownloadApkBetterHelper.reDownLoadApk()
//    }
//
//    override fun onFinish(file: File) {
//        TODO("Not yet implemented")
//    }
//    /**显示安装*/
//    private fun showInstallMode() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val hasInstallPermission: Boolean =
//                context?.packageManager?.canRequestPackageInstalls() ?: false
//            if (!hasInstallPermission) {
//                startInstallPermissionSettingActivity()
//            } else {
//                goInstall()
//            }
//        } else {
//            goInstall()
//        }
//    }
//    /**
//     * 跳转到设置-允许安装未知来源-页面
//     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private fun startInstallPermissionSettingActivity() {
//        val packageURI: Uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
//        //注意这个是8.0新API
//        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
////        startActivityForResult(intent, 1)
//    }
//    private fun goInstall() {
//        DownloadApkBetterHelper.getDownloadFile().absolutePath.run {
//            InstallApkHelper(this).install()
//        }
//    }
//}
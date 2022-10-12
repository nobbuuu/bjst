package com.kredit.cash.loan.app.upgrade

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kredit.cash.loan.app.BuildConfig
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.databinding.PopUpgradeBinding
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.NoViewModel
import com.tcl.base.common.ui.BaseFullScreenDialogFragment
import com.tcl.base.download.DownloadApkBetterHelper
import com.tcl.base.download.IUpgradeListener
import com.tcl.base.download.InstallApkHelper
import com.tcl.base.kt.ktClick
import com.tcl.base.utils.MmkvUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


/**
 *Author:tiaozi
 *DATE: 2021/7/20
 *DRC:
 */
private const val KEY_VERSION_INFO = "versionInfo"

class UpgradeDialogFragment(var dismissCallBack: (() -> Unit)?) :
    BaseFullScreenDialogFragment<NoViewModel, PopUpgradeBinding>(), IUpgradeListener {

    companion object {
        fun newInstance(
            newVersionBean: NewVersionBean,
            dismissCallBack: (() -> Unit)? = null
        ): UpgradeDialogFragment {
            val fragment = UpgradeDialogFragment(dismissCallBack)
            fragment.isCancelable = false
            fragment.arguments = Bundle().apply {
                putSerializable(KEY_VERSION_INFO, newVersionBean)
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val versionInfo = getSerializable(KEY_VERSION_INFO)
            if (versionInfo is NewVersionBean) {
                mBinding.upgradeTitle.text = versionInfo.remarks
                mBinding.newestVersion.text = "Newest version:" + versionInfo.versionNumber

                Log.i(TAG, "onViewCreated: " + versionInfo.isForce())
                if (versionInfo.force == true) {
                    mBinding.noticeFinishImage.visibility = View.GONE
                }
                mBinding.noticeFinishImage.ktClick {
                    MmkvUtil.encode(versionInfo.getCacheKey(), true)
                    dismiss()
                }
                mBinding.sureUpdateNowBtn.ktClick {
                    showDownloadingMode()
                    mBinding.sureUpdateNowBtn.isEnabled = false
                    DownloadApkBetterHelper.downLoadApk()
                }

                val contentAdapter = ContentAdapter()
                mBinding.updateContentRv.apply {
                    adapter = contentAdapter
                }
                contentAdapter.setList(versionInfo.content)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.run {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun dismiss() {
        super.dismiss()
        DownloadApkBetterHelper.cancel()
        dismissCallBack?.invoke()
    }

    private fun showDownloadingMode() {
        mBinding.upgradeNoticeLL.visibility = View.GONE
        mBinding.noticeFinishImage.visibility = View.GONE
        mBinding.upgradeProgressLL.visibility = View.VISIBLE

    }

    override fun onUpdate(progress: Int) {
//        mBinding.tvDownloadingTip.text = "$progress%"
        mBinding.tvDownloadingTip.tvLeft.text = "$progress%"
        mBinding.upgradeProgressBar.progress = progress
        if (progress == 100) {
            showInstallMode()
        }
    }

    /**显示安装*/
    private fun showInstallMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val hasInstallPermission: Boolean =
                activity?.packageManager?.canRequestPackageInstalls() ?: false
            if (!hasInstallPermission) {
                startInstallPermissionSettingActivity()
            } else {
                goInstall()
            }
        } else {
            goInstall()
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startInstallPermissionSettingActivity() {
        val packageURI: Uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        //注意这个是8.0新API
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
        startActivityForResult(intent, 10086)
    }

    private fun goInstall() {
        DownloadApkBetterHelper.getDownloadFile().absolutePath.run {
            InstallApkHelper(this).install()
        }
    }

    override fun onError() {
        showRetryMode()
    }

    /**重试模式*/
    private fun showRetryMode() {
        //异常在子线程回调
        lifecycleScope.launch(Dispatchers.Main) {
            mBinding.sureUpdateNowBtn.isEnabled = true
            mBinding.sureUpdateNowBtn.text = getString(R.string.upgrade_retry)
//            mBinding.tvDownloadingTip.text = getString(R.string.upgrade_network_error)
            mBinding.sureUpdateNowBtn.ktClick {
                mBinding.sureUpdateNowBtn.isEnabled = false
                DownloadApkBetterHelper.reDownLoadApk()
            }
        }
    }

    override fun onFinish(file: File) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10086) {
            dismiss()
            goInstall()
        }
    }

    /**
     * 内部适配器
     */
    inner class ContentAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_update_content) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.upgradeListTv, item)
        }
    }
}
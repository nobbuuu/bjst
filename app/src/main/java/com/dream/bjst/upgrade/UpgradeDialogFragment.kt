package com.dream.bjst.upgrade

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.dream.bjst.BuildConfig
import com.dream.bjst.R
import com.dream.bjst.databinding.PopUpgradeBinding
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
    BaseFullScreenDialogFragment<NoViewModel, PopUpgradeBinding>(),
    IUpgradeListener {
   // constructor() : this(null)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val versionInfo = getSerializable(KEY_VERSION_INFO)
            if (versionInfo is NewVersionBean) {
                mBinding.tvContent.text = versionInfo.remarks
                if (versionInfo.isForce()) {
                    mBinding.btnLeft.visibility = View.GONE
                }
                mBinding.btnLeft.ktClick {
                    MmkvUtil.encode(versionInfo.getCacheKey(), true)
                    dismiss()
                }
                mBinding.btnRight.ktClick {
                    showDownloadingMode()
                    mBinding.btnRight.isEnabled = false
                    DownloadApkBetterHelper.downLoadApk()
                }
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
        mBinding.groupContentTip.visibility = View.GONE
        mBinding.groupDownloading.visibility = View.VISIBLE
    }

    override fun onUpdate(progress: Int) {
        mBinding.tvDownloadingTip.text = "$progress%"
        mBinding.progressBar.progress = progress
        if (progress == 100) {
            showInstallMode()
        }
    }

    /**显示安装*/
    private fun showInstallMode() {
        mBinding.tvTittleDownloading.text = getString(R.string.upgrade_finish)
        mBinding.btnRight.apply {
            isEnabled = true
            text = getString(R.string.upgrade_install)
            ktClick {
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
        startActivityForResult(intent, 1)
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
            mBinding.btnRight.isEnabled = true
            mBinding.btnRight.text = getString(R.string.upgrade_retry)
            mBinding.tvDownloadingTip.text = getString(R.string.upgrade_network_error)
            mBinding.btnRight.ktClick {
                mBinding.btnRight.isEnabled = false
                DownloadApkBetterHelper.reDownLoadApk()
            }
        }
    }

    override fun onFinish(file: File) {

    }
}
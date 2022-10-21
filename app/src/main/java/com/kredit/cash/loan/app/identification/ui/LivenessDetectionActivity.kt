package com.kredit.cash.loan.app.identification.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.didichuxing.doraemonkit.util.GsonUtils
import com.kredit.cash.loan.app.databinding.ActivityLivenessDetectionBinding
import com.kredit.cash.loan.app.identification.bean.DetectionPictureParam
import com.kredit.cash.loan.app.identification.vm.IdentificationViewModel
import com.kredit.cash.loan.app.utils.DetectionFacialUtils
import com.tcl.base.common.ui.BaseActivity
import com.didichuxing.doraemonkit.util.LogUtils
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.liveness.LivenessActivity
import com.kredit.cash.loan.app.liveness.LivenessResult
import com.kredit.cash.loan.app.main.MainActivity
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.ktToastShow
import com.tcl.base.utils.encipher.Base64

class LivenessDetectionActivity :
    BaseActivity<IdentificationViewModel, ActivityLivenessDetectionBinding>() {
    val KEY_TO_DETECT_REQUEST_CODE = 1
    var customerId: Int? = null
    var marketId: Int? = null
    override fun initView(savedInstanceState: Bundle?) {
        DetectionFacialUtils().requestCameraPermission(this);
        initDetection()
    }

    private fun initDetection() {
        val intent = Intent(this, LivenessActivity::class.java)
//        val intent = Intent(this, DFSilentLivenessActivity::class.java)
        startActivityForResult(intent, KEY_TO_DETECT_REQUEST_CODE)
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }

    override fun startObserve() {
        super.startObserve()
        viewModel.detectPictureResult.observe(this) {
            if (it.`869187819880`) {
                ktStartActivity(ApproveContactsActivity::class)
                finish()
            } else {
                //人脸对比失败 请重试
                getString(R.string.detection_try_again).ktToastShow()
                onBackPressed()
            }
        }

    }


    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val app = data?.getSerializableExtra("data") as LivenessResult?
            app?.let {
                val imageResultArr = it.faceImages
                if (imageResultArr != null) {
                    val size = imageResultArr.size;
                    if (size > 0) {
                        val imageResult = imageResultArr[0]
                        val faceImg = Base64.byte2Base64(imageResult.image)
                        val faceEncryptFile = Base64.byte2Base64(it.encryptResult)
                        val param = GsonUtils.toJson(
                            DetectionPictureParam(
                                `92959791BD9993B6958791C2C0` = faceImg,   //人脸图的base64
                                `989D8291BA918787B29D9891B6958791C2C0` = faceEncryptFile //人脸图加密文件base64
                            )
                        )
                        LogUtils.dTag("paramJson", param)
                        viewModel.submitDetectionPicture(param)
                    }
                }
            }
        } else {
            onBackPressed()
            Log.e("onActivityResult", "silent liveness cancel，error code:$resultCode")
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        DetectionFacialUtils().onRequestPermissionsResult(
            this,
            requestCode,
            permissions,
            grantResults
        );
    }

    override fun onBackPressed() {
        ktStartActivity(MainActivity::class)
        super.onBackPressed()
    }


}
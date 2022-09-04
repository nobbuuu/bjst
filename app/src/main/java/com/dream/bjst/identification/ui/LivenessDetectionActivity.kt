package com.dream.bjst.identification.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.didichuxing.doraemonkit.util.GsonUtils
import com.dream.bjst.databinding.ActivityLivenessDetectionBinding
import com.dream.bjst.identification.bean.DetectionPictureParam
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.utils.BitmapUtils
import com.dream.bjst.utils.DetectionFacialUtils

import com.liveness.dflivenesslibrary.DFTransferResultInterface
import com.liveness.dflivenesslibrary.liveness.DFSilentLivenessActivity
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseActivity
import android.util.Base64
import com.didichuxing.doraemonkit.util.LogUtils
import com.tcl.base.kt.ktStartActivity

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
        val bundle = Bundle()
        val intent = Intent(this, DFSilentLivenessActivity::class.java)
        intent.putExtras(bundle)
        //Enable to get image result
        intent.putExtra(DFSilentLivenessActivity.KEY_DETECT_IMAGE_RESULT, true)
        intent.putExtra(DFSilentLivenessActivity.KEY_HINT_MESSAGE_HAS_FACE, "Please hold still")
        intent.putExtra(
            DFSilentLivenessActivity.KEY_HINT_MESSAGE_NO_FACE,
            "Please place your face inside the circle"
        )
        intent.putExtra(
            DFSilentLivenessActivity.KEY_HINT_MESSAGE_FACE_NOT_VALID,
            "Please move away from the screen"
        )
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
            } else {
                //人脸对比失败 请重试

            }
        }

    }


    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var imageBitmap: Bitmap? = null

//        "有返回结果".ktToastShow()
        if (resultCode == RESULT_OK) {
            val app = application as DFTransferResultInterface
            app?.result?.let {
                Log.i(TAG, "onActivityResult: $it")
                val imageResultArr = it.getLivenessImageResults()
                if (imageResultArr != null) {
                    val size = imageResultArr.size;
                    if (size > 0) {
                        val imageResult = imageResultArr[0];
                        val options = BitmapFactory.Options()
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888
                        imageBitmap = BitmapFactory.decodeByteArray(
                            imageResult.image,
                            0,
                            imageResult.image.size,
                            options
                        )
                        Log.i(TAG, "onActivityResult:" + imageBitmap)
                    }
                }
                // the encrypt buffer which is used to send to anti-hack API
                val livenessEncryptResult = it.getLivenessEncryptResult()
                Log.i(TAG, "onActivityResult: " + livenessEncryptResult)
                val base64Str = Base64.encodeToString(livenessEncryptResult, Base64.DEFAULT)
                Log.i(TAG, "onActivityResult:" + base64Str)
                val param = GsonUtils.toJson(
                    DetectionPictureParam(
                        `92959791BD9993B6958791C2C0` = BitmapUtils.bitmapToBase64(imageBitmap),   //人脸图的base64
                        `989D8291BA918787B29D9891B6958791C2C0` = base64Str //人脸图加密文件base64
                    )
                )
                LogUtils.dTag("paramJson",param)
                viewModel.submitDetectionPicture(param)
            }


        } else {
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


}
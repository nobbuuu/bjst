package com.dream.bjst.identification.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.dream.bjst.app.MyApp
import com.dream.bjst.databinding.ActivityLivenessDetectionBinding
import com.dream.bjst.identification.vm.LivenessDetectionViewModel
import com.dream.bjst.utils.DetectionFacialUtils
import com.liveness.dflivenesslibrary.DFTransferResultInterface
import com.liveness.dflivenesslibrary.liveness.DFSilentLivenessActivity
import com.liveness.dflivenesslibrary.utils.DFBitmapUtils
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktToastShow


class LivenessDetectionActivity :
    BaseActivity<LivenessDetectionViewModel, ActivityLivenessDetectionBinding>() {
    val KEY_TO_DETECT_REQUEST_CODE = 1

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
        intent.putExtra(DFSilentLivenessActivity.KEY_HINT_MESSAGE_NO_FACE, "Please place your face inside the circle")
        intent.putExtra(DFSilentLivenessActivity.KEY_HINT_MESSAGE_FACE_NOT_VALID, "Please move away from the screen")
        startActivityForResult(intent, KEY_TO_DETECT_REQUEST_CODE)
    }

    override fun initData() {

    }

    override fun initDataOnResume() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        "有返回结果".ktToastShow()
        if (resultCode == RESULT_OK) {
            val app = application as DFTransferResultInterface
            app?.result?.let {
                Log.i(TAG, "onActivityResult: $it")
                val imageResultArr = it.livenessImageResults
                if (imageResultArr != null) {
                    val size = imageResultArr.size
                    if (size > 0) {
                        val imageResult = imageResultArr[0]
                        val options = BitmapFactory.Options()
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888
                        val imageBitmap = BitmapFactory.decodeByteArray(
                            imageResult.image,
                            0,
                            imageResult.image.size,
                            options
                        )
                        DFBitmapUtils.recyleBitmap(imageBitmap)
                        mBinding.image.setImageBitmap(imageBitmap)
                    }
                }
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
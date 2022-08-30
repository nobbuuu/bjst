package com.dream.bjst.identification.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.dfsdk.liveness.DFLivenessSDK
import com.dream.bjst.app.MyApp
import com.dream.bjst.databinding.ActivityLivenessDetectionBinding
import com.dream.bjst.identification.vm.LivenessDetectionViewModel
import com.liveness.dflivenesslibrary.DFProductResult
import com.liveness.dflivenesslibrary.DFTransferResultInterface
import com.liveness.dflivenesslibrary.liveness.DFSilentLivenessActivity
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick


class LivenessDetectionActivity :
    BaseActivity<LivenessDetectionViewModel, ActivityLivenessDetectionBinding>() {
    val KEY_TO_DETECT_REQUEST_CODE = 1

    override fun initView(savedInstanceState: Bundle?) {
//     mBinding.titleLive.leftView.ktClick {
//         onBackPressed()
//     }
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

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        var mResult: DFProductResult = MyApp().result
        Log.i(TAG, "onActivityResult: " + mResult)

        ///get key frame

        var imageResultArr = mResult.livenessImageResults
        if (imageResultArr != null) {
            var size = imageResultArr.size;
            if (size > 0) {

                var imageResult: DFLivenessSDK.DFLivenessImageResult = imageResultArr[0];
                var imageBitmap: Bitmap =
                    BitmapFactory.decodeByteArray(imageResult.image, 0, imageResult.image.size);


            }
        }

        // the encrypt buffer which is used to send to anti-hack API .getLivenessEncryptResult()
        var livenessEncryptResult: ByteArray? = mResult.livenessEncryptResult
    }


}
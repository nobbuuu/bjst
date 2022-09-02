package com.dream.bjst.identification.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.didichuxing.doraemonkit.util.GsonUtils
import com.dream.bjst.databinding.ActivityLivenessDetectionBinding
import com.dream.bjst.identification.bean.DetectionPictureParam
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.utils.BitmapUtils
import com.dream.bjst.utils.DetectionFacialUtils
import com.dream.bjst.utils.FileUtils
import com.liveness.dflivenesslibrary.DFTransferResultInterface
import com.liveness.dflivenesslibrary.liveness.DFSilentLivenessActivity
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseActivity
import java.io.*


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

        }

    }


    /**
     * 将Bitmap类型的图片转化成file类型，便于上传到服务器
     */
    fun saveImageFile(imageBitmap: Bitmap, fileName: String): File {
        var path = Environment.getExternalStorageState() + "image"
        var dirFile = File(path)
        if (!dirFile.exists()) {
            dirFile.mkdir()
        }
        var imageFile = File(path + fileName)
        var bos = BufferedOutputStream(FileOutputStream(imageFile))
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        bos.flush()
        bos.close()

        return imageFile
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
                        val imageBitmap = BitmapFactory.decodeByteArray(
                            imageResult.image,
                            0,
                            imageResult.image.size,
                            options
                        );

                        var imageFile = saveImageFile(imageBitmap, "imageIcon")

                        Log.i(TAG, "onActivityResult0001: " + imageFile)
                        viewModel.submitDetectionPicture(
                            GsonUtils.toJson(
                                DetectionPictureParam(
                                    `92959791BD9993B6958791C2C0` = BitmapUtils.bitmapToBase64(imageBitmap),   //人脸图的base64
                                    `989D8291BA918787B29D9891B6958791C2C0` = FileUtils.encodeFileToBase64(saveImageFile(imageBitmap,"imageIcon").path) //文件base64
                                )
                            )

                        )



                    }
                }
                // the encrypt buffer which is used to send to anti-hack API
                val livenessEncryptResult = it.getLivenessEncryptResult()


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
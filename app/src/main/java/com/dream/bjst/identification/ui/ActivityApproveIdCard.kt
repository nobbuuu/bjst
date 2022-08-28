package com.dream.bjst.identification.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.dream.bjst.common.Constant
import com.dream.bjst.databinding.ActivityCertificationIdcardBinding
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.utils.FileUtils.bitmap2File
import com.dream.bjst.utils.PhotoManager
import com.dream.bjst.utils.PhotoSelectDialog
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.utils.PhotoUtils.getPath

class ActivityApproveIdCard :
    BaseActivity<IdentificationViewModel, ActivityCertificationIdcardBinding>() {
    lateinit var mPhotoManager: PhotoManager
    lateinit var photoDialog :PhotoSelectDialog
    private var type = 0
    override fun initView(savedInstanceState: Bundle?) {
        mPhotoManager = PhotoManager(this)
        photoDialog = PhotoSelectDialog(this, PhotoSelectDialog.PICK_AVATAR)
        photoDialog.mSelectPicture.ktClick {
            mPhotoManager.checkPermissionAndChosePhoto()
            photoDialog.dismiss()
        }
        photoDialog.mSelectCamera.ktClick {
            mPhotoManager.checkPermissionAndCamera()
            photoDialog.dismiss()
        }
        mBinding.frontIv.ktClick {
            type = 1
            photoDialog.show()
        }
        mBinding.backIv.ktClick {
            type = 2
            photoDialog.show()
        }
        mBinding.panCardIv.ktClick {
            type = 3
            photoDialog.show()
        }
    }

    override fun initData() {
    }

    override fun initDataOnResume() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.CAMERA_REQUEST_CODE) {
                if (mPhotoManager.isAndroidQ) {
                    // Android 10 使用图片uri加载
                    val bitmap = mPhotoManager.getBitmapFromUri(mPhotoManager.mCameraUri)
                    bitmap?.let { upLoadFile(it) }
                } else {
                    // 使用图片路径加载
                    val rotateBitmap =
                        mPhotoManager.rotateBitmap(BitmapFactory.decodeFile(mPhotoManager.mCameraImagePath))
                    upLoadFile(rotateBitmap)
                }
            }
            if (requestCode == Constant.CHOOSE_PHOTO_CODE) {
                val result = data!!.data
                if (result != null) {
                    val path = getPath(this, result)
                    val rotateBitmap = BitmapFactory.decodeFile(path)
                    upLoadFile(rotateBitmap)
                }
            }
        }
    }

    private fun upLoadFile(bitmap: Bitmap?) {
        val rotateBitmap = mPhotoManager.rotateBitmap(bitmap!!)
        //显示图片
        when(type){
            1 ->{
                mBinding.frontIv.setImageBitmap(rotateBitmap)
            }
            2 ->{
                mBinding.backIv.setImageBitmap(rotateBitmap)
            }
            3 ->{
                mBinding.panCardIv.setImageBitmap(rotateBitmap)
            }
        }
        val file = bitmap2File(
            rotateBitmap!!,
            filesDir.path,
            "tempAvatar.png"
        )
        //上传图片
    }
}
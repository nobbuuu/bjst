package com.dream.bjst.identification.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.dream.bjst.common.Constant
import com.dream.bjst.databinding.ActivityCertificationIdcardBinding
import com.dream.bjst.identification.vm.IdentificationViewModel
import com.dream.bjst.utils.BitmapUtils
import com.dream.bjst.utils.FileUtils.bitmap2File
import com.dream.bjst.utils.PhotoManager
import com.dream.bjst.utils.PhotoSelectDialog
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktToastShow
import com.tcl.base.utils.PhotoUtils.getPath

class ApproveIdCardActivity :
    BaseActivity<IdentificationViewModel, ActivityCertificationIdcardBinding>() {
    lateinit var mPhotoManager: PhotoManager
    lateinit var photoDialog: PhotoSelectDialog
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

    /**
     * 处理权限申请的回调。
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constant.PERMISSION_CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //允许权限，有调起相机拍照。
                    mPhotoManager.openCamera()
                } else {
                    //拒绝权限，弹出提示框。
                    "The photo permission is denied".ktToastShow()
                }
            }

            Constant.PERMISSION_STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //允许权限
                    mPhotoManager.goPhotoAlbum()
                } else {
                    //拒绝权限，弹出提示框。
                    "The storage permission is denied".ktToastShow()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.CAMERA_REQUEST_CODE) {
                mPhotoManager.getBitmapWithCamara()?.let {
                    upLoadFile(it)
                }
            }
            if (requestCode == Constant.CHOOSE_PHOTO_CODE) {
                data?.data?.let {
                    val path = getPath(this, it)
                    val rotateBitmap = BitmapFactory.decodeFile(path)
                    upLoadFile(rotateBitmap)
                }
            }
        }
    }

    private fun upLoadFile(bitmap: Bitmap?) {
        //显示图片
        when (type) {
            1 -> {
                mBinding.frontIv.setImageBitmap(bitmap)
            }
            2 -> {
                mBinding.backIv.setImageBitmap(bitmap)
            }
            3 -> {
                mBinding.panCardIv.setImageBitmap(bitmap)
            }
        }
        val base64 = BitmapUtils.bitmapToBase64(bitmap)
        LogUtils.dTag("basss",base64)
       /* val file = bitmap2File(
            rotateBitmap!!,
            filesDir.path,
            "tempAvatar.png"
        )*/
        //上传图片
    }
}
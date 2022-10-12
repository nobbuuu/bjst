package com.kredit.cash.loan.app.identification.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.databinding.ActivityCertificationIdcardBinding
import com.kredit.cash.loan.app.identification.bean.IdCardInfoParam
import com.kredit.cash.loan.app.identification.vm.IdentificationViewModel
import com.kredit.cash.loan.app.utils.PhotoManager
import com.kredit.cash.loan.app.utils.PhotoSelectDialog
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.*
import com.tcl.base.utils.PhotoUtils.getPath
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream

class ApproveIdCardActivity :
    BaseActivity<IdentificationViewModel, ActivityCertificationIdcardBinding>() {
    lateinit var mPhotoManager: PhotoManager
    lateinit var photoDialog: PhotoSelectDialog
    private var type = 0
    private var tempBitmap: Bitmap? = null
    private var isFront = false
    private var isBack = false
    private var isPan = false
    override fun initView(savedInstanceState: Bundle?) {
        mPhotoManager = PhotoManager(this)
        photoDialog = PhotoSelectDialog(
            this,
            PhotoSelectDialog.PICK_AVATAR
        )
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

        viewModel.fetchCustomerKycStatus()
        mBinding.sureBtn.ktClick {
            if (isApproveComplete()) {
                ktStartActivity(ApproveIdCardConfirmActivity::class)
            }
        }
    }

    fun isApproveComplete(): Boolean {
        if (!isFront) {
            "Please upload the front photo first".ktToastShow()
            return false
        }
        if (!isBack) {
            "Please upload the back photo first".ktToastShow()
            return false
        }
        if (!isPan) {
            "Please upload the pan photo first".ktToastShow()
            return false
        }
        return true
    }

    override fun initDataOnResume() {
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.idCardInfo.observe(this) {
            when (type) {
                1 -> {
                    mBinding.frontIv.setImageBitmap(tempBitmap)
                    isFront = true
                    mBinding.frontIv.isEnabled = !isFront
                }
                2 -> {
                    mBinding.backIv.setImageBitmap(tempBitmap)
                    isBack = true
                    mBinding.backIv.isEnabled = !isBack
                }
                3 -> {
                    mBinding.panCardIv.setImageBitmap(tempBitmap)
                    isPan = true
                    mBinding.panCardIv.isEnabled = !isPan
                }
            }
        }

        viewModel.idCardStatus.observe(this) {
            it.`9D90B7958690B2869B9A80A49C9B809BA18698`?.let {
                mBinding.frontIv.loadGif(it)
                isFront = true
            }
            it.`9D90B7958690B695979FA49C9B809BA18698`?.let {
                mBinding.backIv.loadGif(it)
                isBack = true
            }
            it.`84959AA49C9B809BA18698`?.let {
                mBinding.panCardIv.loadGif(it)
                isPan = true
            }
            mBinding.frontIv.isEnabled = !isFront
            mBinding.backIv.isEnabled = !isBack
            mBinding.panCardIv.isEnabled = !isPan
        }
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
        tempBitmap = bitmap
        val base64 = com.kredit.cash.loan.app.utils.BitmapUtils.bitmapToBase64(bitmap).remove()
        val bitmapStr = compress(base64)
        //显示图片
        when (type) {
            1 -> {
                viewModel.idCardFrontOcr(GsonUtils.toJson(IdCardInfoParam(`9D99959391B6958791C2C0` = bitmapStr.nullToEmpty())))
            }
            2 -> {
                viewModel.idCardBackOcr(GsonUtils.toJson(IdCardInfoParam(`9D99959391B6958791C2C0` = bitmapStr.nullToEmpty())))
            }
            3 -> {
                viewModel.panOcr(GsonUtils.toJson(IdCardInfoParam(`9D99959391B6958791C2C0` = bitmapStr.nullToEmpty())))
            }
        }
    }

    fun compress(str: String?): String? {
        if (str.isNullOrEmpty()) {
            return str
        }
        val o = ByteArrayOutputStream()
        val g = GZIPOutputStream(o)
        g.write(str.toByteArray(charset("utf-8")))
        g.close()
        return o.toString("ISO-8859-1")
    }
}
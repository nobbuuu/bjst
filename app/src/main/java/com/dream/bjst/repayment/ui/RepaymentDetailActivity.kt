package com.dream.bjst.repayment.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.dream.bjst.R
import com.dream.bjst.common.Constant
import com.dream.bjst.databinding.ActivityRepaymentDetailBinding
import com.dream.bjst.repayment.bean.RepaymentDetailParam
import com.dream.bjst.repayment.vm.RepaymentViewModel
import com.dream.bjst.utils.FileUtils.bitmap2File
import com.dream.bjst.utils.PhotoManager
import com.dream.bjst.utils.PhotoSelectDialog
import com.liveness.dflivenesslibrary.view.TimeViewContoller.TAG
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktSetImageIf
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.loadGif
import com.tcl.base.utils.MmkvUtil.encode
import com.tcl.base.utils.PhotoUtils.getPath

/**
 * 创建日期：2022-09-05 on 1:03
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class RepaymentDetailActivity : BaseActivity<RepaymentViewModel, ActivityRepaymentDetailBinding>() {
    var mPhotoManager: PhotoManager? = null

    //输入框里面的内容
    var temp: CharSequence = ""
    var startEdit = 0
    var endEdit = 0

    override fun initView(savedInstanceState: Bundle?) {

        var param: String = GsonUtils.toJson(
            RepaymentDetailParam(
                `969B86869B83BD90` = intent.getStringExtra("detailId")
            )
        )
        viewModel.repaymentDetailData(param)

        event()
        mPhotoManager = PhotoManager(this)
        mBinding.digitalEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                temp = s
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable) {
                startEdit = mBinding.digitalEt.selectionStart
                endEdit = mBinding.digitalEt.selectionEnd
                //mNumTextView.setText(String.valueOf(temp.length()));
                if (temp.length > 12) {
                    s.delete(startEdit - 1, endEdit)
                    val tempSelection = startEdit
                    mBinding.digitalEt.setText(s)
                    mBinding.digitalEt.setSelection(tempSelection)
                    ToastUtils.showShort("你输入的字已经超过了！")
                } else if (temp.length < 12) {
                    mBinding.notionTv.visibility = View.VISIBLE
                    mBinding.notionTv.text = "Enter the correct No."
                } else {
                    mBinding.notionTv.visibility = View.VISIBLE
                    mBinding.notionTv.text = "Enter is correct！"
                    mBinding.notionTv.setTextColor(R.color.green_color_background)
                }
            }
        })
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentDetailResult.observe(this) {
            mBinding.repaymentDetailIcon.loadGif(it.`9D979BA18698`)
            mBinding.repaymentDetailName.text = it.`84869B90819780BA959991`
            mBinding.repaymentDetailAmount.text = "₹ " + it.`84869D9A979D849598B5999B819A80`
            mBinding.repaymentDetailLoanAmount.text = "₹ " + it.`84869D9A979D849598B5999B819A80`
            mBinding.repaymentDetailLoanDay.text = it.`869184958DB19A90`
            mBinding.repaymentDetailOverDueAmount.text = "₹ " + it.`869199959D9ABB829186908191`
            when(it.`9B86909186A78095808187`){
                "10" -> mBinding.repaymentDetailStatus.text="NotDue"
                "20" -> mBinding.repaymentDetailStatus.text="DueToday"
                "30" -> mBinding.repaymentDetailStatus.text="OverDue"
            }


        }

    }


    /**
     * 事件点击
     */
    private fun event() {
        mBinding.titleBar.leftView.setOnClickListener(View.OnClickListener { onBackPressed() })

        mBinding.confirmPayButton.ktClick {
            ktStartActivity(ExtendRePaymentActivity::class)
        }
        //repaidButton
        mBinding.repaidSubmitButton.setOnClickListener(View.OnClickListener {
            encode(
                "digital",
                temp
            )
        })
        //点击上传UTR_picture
        mBinding.addPictureCameraIv.setOnClickListener(View.OnClickListener { //这里上传头
            val photoSelectDialog =
                PhotoSelectDialog(this@RepaymentDetailActivity, PhotoSelectDialog.PICK_AVATAR)
            photoSelectDialog.show()
            photoSelectDialog.mSelectPicture.setOnClickListener {
                mPhotoManager?.checkPermissionAndChosePhoto()
                photoSelectDialog.dismiss()
            }
            photoSelectDialog.mSelectCamera.setOnClickListener {
                mPhotoManager?.checkPermissionAndCamera()
                photoSelectDialog.dismiss()
            }
        })

        //设置头布局和尾部局的显示和隐藏
        mBinding.headerArrow.setOnClickListener(View.OnClickListener {
            mBinding.headerRv.setVisibility(View.GONE)
            mBinding.footerArrow.setImageResource(R.mipmap.ic_up_two_level)
            mBinding.footerRv.setVisibility(View.VISIBLE)
        })

        //设置头布局和尾部局的显示和隐藏
        mBinding.footerArrow.setOnClickListener(View.OnClickListener {
            mBinding.footerRv.setVisibility(View.GONE)
            mBinding.headerRv.setVisibility(View.VISIBLE)
            mBinding.headerArrow.setImageResource(R.mipmap.ic_up_one_level)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限，有调起相机拍照。
                mPhotoManager?.openCamera()
            } else {
                //拒绝权限，弹出提示框。
                ToastUtils.showShort("The photo permission is denied")
            }
        }
        if (requestCode == Constant.PERMISSION_STORAGE_REQUEST_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限
                mPhotoManager?.goPhotoAlbum()
            } else {
                //拒绝权限，弹出提示框。
                ToastUtils.showShort("The storage permission is denied")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.CAMERA_REQUEST_CODE) {
                mPhotoManager?.getBitmapWithCamara()?.let {
                    upLoadFile(it)
                }
            }
            if (requestCode == Constant.CHOOSE_PHOTO_CODE) {
                val result = data?.data
                if (result != null) {
                    val path = getPath(this, result)
                    val rotateBitmap = BitmapFactory.decodeFile(path)
                    upLoadFile(rotateBitmap)
                }
            }
        }
    }

    private fun upLoadFile(bitmap: Bitmap) {
        val rotateBitmap = mPhotoManager?.rotateBitmap(bitmap)
        //显示图片
        mBinding.addPictureCameraIv.setImageBitmap(rotateBitmap)
        rotateBitmap?.let {
            val file = bitmap2File(
                it,
                filesDir.path,
                "tempAvatar.png"
            )
            //上传图片
        }
    }

    override fun initData() {
    }

    override fun initDataOnResume() {
    }
}
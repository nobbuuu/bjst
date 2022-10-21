package com.kredit.cash.loan.app.repayment.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kredit.cash.loan.app.R
import com.kredit.cash.loan.app.R.color.colorPrimaryDark
import com.kredit.cash.loan.app.common.Constant
import com.kredit.cash.loan.app.databinding.ActivityRepaymentDetailBinding
import com.kredit.cash.loan.app.other.WebViewActivity
import com.kredit.cash.loan.app.repayment.bean.PaymentUtrParam
import com.kredit.cash.loan.app.repayment.bean.RepaymentDetailParam
import com.kredit.cash.loan.app.repayment.bean.requestRepaymentParam
import com.kredit.cash.loan.app.repayment.vm.RepaymentViewModel
import com.kredit.cash.loan.app.utils.BitmapUtils
import com.kredit.cash.loan.app.utils.CompressIconUtils.bitmap2Byte
import com.kredit.cash.loan.app.utils.PhotoManager
import com.kredit.cash.loan.app.utils.PhotoSelectDialog
import com.tcl.base.common.ui.BaseActivity
import com.tcl.base.kt.ktClick
import com.tcl.base.kt.ktStartActivity
import com.tcl.base.kt.loadGif
import com.tcl.base.utils.PhotoUtils.getPath
import com.tcl.base.utils.encipher.Base64.byte2Base64

/**
 * 创建日期：2022-09-05 on 1:03
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
class RepaymentDetailActivity : BaseActivity<RepaymentViewModel, ActivityRepaymentDetailBinding>() {
    var mPhotoManager: PhotoManager? = null
    var borrowId: String? = null
    var bitmapStr: String? = null
    var utrCode: String? = null
    var UTRParam: String = ""


    //输入框里面的内容
    var temp: CharSequence = ""
    var startEdit = 0
    var endEdit = 0

    override fun initView(savedInstanceState: Bundle?) {

        event()//点击事件函数
        var param: String =
            GsonUtils.toJson(RepaymentDetailParam(intent.getStringExtra("detailId")))
        viewModel.repaymentDetailData(param)

        mPhotoManager = PhotoManager(this)
        /**
         * 设置UTR的数字监听
         */
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
                    mBinding.digitalEt.text = s
                    mBinding.digitalEt.setSelection(tempSelection)
                    ToastUtils.showShort("你输入的字已经超过了！")
                } else if (temp.length < 12) {
                    mBinding.notionTv.visibility = View.VISIBLE
                    mBinding.notionTv.text = "Enter the correct No."
                } else {
                    mBinding.notionTv.visibility = View.VISIBLE
                    mBinding.notionTv.text = "Enter is correct！"
                    mBinding.notionTv.setTextColor(colorPrimaryDark)
                }

            }
        })


    }

    override fun startObserve() {
        super.startObserve()
        viewModel.repaymentDetailResult.observe(this) {
            borrowId = it.`969B86869B83BD90`
            mBinding.repaymentDetailIcon.loadGif(it.`9D979BA18698`)
            mBinding.repaymentDetailName.text = it.`84869B90819780BA959991`
            mBinding.repaymentDetailAmount.text = "₹ " + it.`869199959D9AA09B809598B5999B819A80`
            mBinding.repaymentDetailLoanAmount.text = "₹ " + it.`84869D9A979D849598B5999B819A80`
            mBinding.repaymentDetailLoanDay.text = it.`869184958DB19A90`
            mBinding.repaymentDetailOverDueAmount.text = "₹ " + it.`869199959D9ABB829186908191`

            when (it.`9B86909186A78095808187`) {
                "10" -> mBinding.repaymentDetailStatus.text = "Not Due"
                "20" -> mBinding.repaymentDetailStatus.text = "Due Today"
                "30" -> mBinding.repaymentDetailStatus.text = "Over Due"
            }
        }
        /**
         * 上传utr返回数据
         */
        viewModel.repaymentUTRResult.observe(this) {

            it.takeIf { it.`869187819880` }?.let {
                ToastUtils.showShort("Submit successfully")
                mBinding.footerRv.setVisibility(View.GONE)
                mBinding.headerRv.setVisibility(View.VISIBLE)
                mBinding.digitalEt.text = null
                mBinding.addPictureCameraIv.setImageResource(R.mipmap.addpicturecamer)
                mBinding.headerArrow.setImageResource(R.mipmap.ic_up_one_level)
            } ?: ToastUtils.showShort("Submit utr failed")

        }

        /**
         * 正常还款数据
         */
        viewModel.reqRepaymentResult.observe(this) {

            when (it.`84958DA08D8491`) {
                "0" -> {
                    ktStartActivity(WebViewActivity::class) {
                        putExtra("webUrl", it.`84958DB89D9A9F`)
                        putExtra("title", "Extend")
                        putExtra("jsEnable", true)
                    }

                }
                "1" -> {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.data = (Uri.parse(it.`84958DB89D9A9F`))
                        startActivity(intent)
                    } catch (e: Exception) {
                        println("There is no browser on the current phone!")
                    }

                }
            }
        }

    }


    /**
     * 事件点击
     */
    @SuppressLint("ResourceAsColor")
    private fun event() {
//        mBinding.titleBar.leftView.setOnClickListener(View.OnClickListener { onBackPressed() })
        mBinding.titleBar.leftView.ktClick {
            onBackPressed()
        }
        /**
         * 正常还款点击按钮
         */
        mBinding.confirmPayButton.ktClick {
            var reqPaymentParam = GsonUtils.toJson(
                requestRepaymentParam(
                    `969B86869B83BD90` = borrowId,
                    `869184958DA08D8491` = 10
                )
            )
            viewModel.reqPaymentData(reqPaymentParam)
        }


        /**
         * 延期还款点击按钮
         */
        mBinding.deferPayButton.ktClick {
            ktStartActivity(ExtendRePaymentActivity::class) {
                putExtra("borrowId", borrowId)
            }
        }
        //repaidButton Submit按钮
        mBinding.repaidSubmitButton.ktClick {
            utrCode = mBinding.digitalEt.text.toString()
            UTRParam = GsonUtils.toJson(
                PaymentUtrParam(
                    `969B86869B83BD90` = borrowId,
                    `818086B79B9091` = utrCode,
                    `818086BD9993A18698B6958791C2C0` = bitmapStr,
                    `979B9984869187879190` = true

                )
            )
            viewModel.paymentUTRData(UTRParam)

            if (utrCode!!.isEmpty() && bitmapStr?.length == null) {
                mBinding.notionTv.visibility = View.VISIBLE
                mBinding.pictureTv.visibility = View.VISIBLE
                //ToastUtils.showShort("Submit utr failed")                //ToastUtils.showShort("Submit successfully")
            } else {

            }
        }
        //点击上传UTR_picture
        mBinding.addPictureCameraIv.setOnClickListener(View.OnClickListener { //这里上传头
            val photoSelectDialog = PhotoSelectDialog(
                this@RepaymentDetailActivity,
                PhotoSelectDialog.PICK_AVATAR
            )
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
        val compressBitmap = BitmapUtils.compressImage(rotateBitmap)
        compressBitmap?.let {
            bitmapStr = byte2Base64(bitmap2Byte(it))
            //上传图片
        }
    }


    override fun initData() {

    }

    override fun initDataOnResume() {
    }
}
package com.dream.bjst.repayment.ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.common.Constant;
import com.dream.bjst.utils.FileUtils;
import com.dream.bjst.utils.PhotoSelectDialog;
import com.dream.bjst.utils.PhotoManager;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.tcl.base.utils.MmkvUtil;
import com.tcl.base.utils.PhotoUtils;

import java.io.File;

public class RepaymentDetailActivity extends BaseActivity {
    private TitleBar mTitleBar;
    private REditText mDigitalEt;
    private RTextView notionTv;
    private Button mPayButton, mRepaidSubmitButton;
    private RImageView mRImageView, mFooterArrow, mHeaderArrow;
    private PhotoManager mPhotoManager;

    private RelativeLayout mHeaderLayout, mFooterLayout;
    //输入框里面的内容
    CharSequence temp;
    int startEdit;
    int endEdit;

    @Override
    protected int initLayout() {
        //调整状态栏的字体颜色为黑色
        StatusBarUtils.adjustWindow(this, true);
        return R.layout.activity_repayment_detail;
    }

    @Override
    protected void initView() {
        mPhotoManager = new PhotoManager(this);
        mTitleBar = fvbi(R.id.title_bar);
        mDigitalEt = fvbi(R.id.digital_et);
        notionTv = fvbi(R.id.notion_tv);
        mPayButton = fvbi(R.id.confirm_pay_button);
        mRepaidSubmitButton = fvbi(R.id.repaid_submit_button);
        mRImageView = fvbi(R.id.add_picture_camera_iv);
        //尾部局箭头
        mFooterArrow = fvbi(R.id.footer_arrow);
        mHeaderArrow = fvbi(R.id.header_arrow);

        //这里是设置头布局和尾部局的显示隐藏
        mHeaderLayout = fvbi(R.id.header_rv);
        mFooterLayout = fvbi(R.id.footer_rv);
        //显示存储数据
        mDigitalEt.setText(MmkvUtil.INSTANCE.decodeString("digital"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtils.showShort(MmkvUtil.INSTANCE.decodeString("digital"));
        mDigitalEt.setText(MmkvUtil.INSTANCE.decodeString("digital"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDigitalEt.setText(MmkvUtil.INSTANCE.decodeString("digital"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mDigitalEt.setText(MmkvUtil.INSTANCE.decodeString("digital"));
    }

    @Override
    protected void initData() {
        event();
        mDigitalEt.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }


            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                startEdit = mDigitalEt.getSelectionStart();
                endEdit = mDigitalEt.getSelectionEnd();
                //mNumTextView.setText(String.valueOf(temp.length()));
                if (temp.length() > 12) {
                    s.delete(startEdit - 1, endEdit);
                    int tempSelection = startEdit;
                    mDigitalEt.setText(s);
                    mDigitalEt.setSelection(tempSelection);
                    ToastUtils.showShort("你输入的字已经超过了！");

                } else if (temp.length() < 12) {
                    notionTv.setVisibility(View.VISIBLE);
                    notionTv.setText("Enter the correct No.");

                } else {
                    notionTv.setVisibility(View.VISIBLE);
                    notionTv.setText("Enter is correct！");
                    notionTv.setTextColor(R.color.green_color_background);

                }


            }
        });
    }

    private void event() {
        mTitleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //确定提交按钮
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RepaymentDetailActivity.this, ExtendRePaymentActivity.class));
            }
        });
        //repaidButton
        mRepaidSubmitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                MmkvUtil.INSTANCE.encode("digital", temp);
            }
        });
        //点击上传UTR_picture
        mRImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里上传头
                PhotoSelectDialog photoSelectDialog = new PhotoSelectDialog(RepaymentDetailActivity.this, PhotoSelectDialog.PICK_AVATAR);
                photoSelectDialog.show();
                photoSelectDialog.mSelectPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhotoManager.checkPermissionAndChosePhoto();
                        photoSelectDialog.dismiss();
                    }
                });

                photoSelectDialog.mSelectCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhotoManager.checkPermissionAndCamera();
                        photoSelectDialog.dismiss();
                    }
                });
            }
        });

        //设置头布局和尾部局的显示和隐藏
        mHeaderArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeaderLayout.setVisibility(View.GONE);
                mFooterArrow.setImageResource(R.mipmap.ic_up_two_level);
                mFooterLayout.setVisibility(View.VISIBLE);

            }
        });

        //设置头布局和尾部局的显示和隐藏
        mFooterArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFooterLayout.setVisibility(View.GONE);
                mHeaderLayout.setVisibility(View.VISIBLE);
                mHeaderArrow.setImageResource(R.mipmap.ic_up_one_level);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限，有调起相机拍照。
                mPhotoManager.openCamera();
            } else {
                //拒绝权限，弹出提示框。
                ToastUtils.showShort("The photo permission is denied");
            }
        }

        if (requestCode == Constant.PERMISSION_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限
                mPhotoManager.goPhotoAlbum();
            } else {
                //拒绝权限，弹出提示框。
                ToastUtils.showShort("The storage permission is denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.CAMERA_REQUEST_CODE) {
                if (mPhotoManager.isAndroidQ) {
                    // Android 10 使用图片uri加载
                    Bitmap bitmap = mPhotoManager.getBitmapFromUri(mPhotoManager.mCameraUri);
                    if (bitmap != null) {
                        upLoadFile(bitmap);
                    }

                } else {
                    // 使用图片路径加载
                    Bitmap rotateBitmap = mPhotoManager.rotateBitmap(BitmapFactory.decodeFile(mPhotoManager.mCameraImagePath));
                    upLoadFile(rotateBitmap);
                }
            }

            if (requestCode == Constant.CHOOSE_PHOTO_CODE) {
                Uri result = data.getData();
                if (result != null) {
                    String path = PhotoUtils.getPath(this, result);
                    Bitmap rotateBitmap = BitmapFactory.decodeFile(path);
                    upLoadFile(rotateBitmap);
                }
            }
        }
    }

    private void upLoadFile(Bitmap bitmap) {
        Bitmap rotateBitmap = mPhotoManager.rotateBitmap(bitmap);
        //显示图片
        mRImageView.setImageBitmap(rotateBitmap);
        File file = FileUtils.bitmap2File(
                rotateBitmap,
                getFilesDir().getPath(),
                "tempAvatar.png"
        );
        //上传图片

    }
}
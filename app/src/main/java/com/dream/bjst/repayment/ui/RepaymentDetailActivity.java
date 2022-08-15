package com.dream.bjst.repayment.ui;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.ToastUtils;
import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.common.Constant;
import com.dream.bjst.utils.FileUtils;
import com.dream.bjst.utils.MyDialog;
import com.dream.bjst.utils.PhotoManager;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.tcl.base.utils.PhotoUtils;
import java.io.File;

public class RepaymentDetailActivity extends BaseActivity {
    private TitleBar mTitleBar;
    private REditText mDigitalEt;
    private RTextView notionTv;
    private Button mPayButton, mRepaidSubmitButton;
    private String inputContent;
    private RImageView mRImageView;
    private PhotoManager mPhotoManager;

    @Override
    protected int initLayout() {
        //调整状态栏的字体颜色为黑色
        new StatusBarUtils().adjustWindow(this, true);
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
    }

    @Override
    protected void initData() {
        event();

        inputContent = mDigitalEt.getText().toString();
        mDigitalEt.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int startEdit;
            int endEdit;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
//                if (count<12){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setText("Enter the correct No.");
//                    ToastUtils.showShort(count);
//                }else if (count==12){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setText("Enter is correct！");
//                    notionTv.setTextColor(R.color.green_color_background);
//                }else return;
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

            }
        });
        //点击上传UTR_picture
        mRImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里上传头
                MyDialog myDialog = new MyDialog(RepaymentDetailActivity.this, MyDialog.PICK_AVATAR);
                myDialog.show();
                myDialog.mSelectPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhotoManager.checkPermissionAndChosePhoto();
                        myDialog.dismiss();
                    }
                });

                myDialog.mSelectCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhotoManager.checkPermissionAndCamera();
                        myDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Constant.CAMERA_REQUEST_CODE){
                if (mPhotoManager.isAndroidQ) {
                    // Android 10 使用图片uri加载
                    Bitmap  bitmap = mPhotoManager.getBitmapFromUri(mPhotoManager.mCameraUri);
                    if (bitmap != null){
                        upLoadFile(bitmap);
                    }

                } else {
                    // 使用图片路径加载
                    Bitmap rotateBitmap = mPhotoManager.rotateBitmap(BitmapFactory.decodeFile(mPhotoManager.mCameraImagePath));
                    upLoadFile(rotateBitmap);
                }
            }

            if (requestCode == Constant.CHOOSE_PHOTO_CODE){
                Uri result = data.getData();
                if (result!= null){
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
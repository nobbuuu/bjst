package com.kredit.cash.loan.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kredit.cash.loan.app.R;


/**
 * 创建日期：2022-07-07 on 0:45
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
public class PhotoSelectDialog {

    public final static int PICK_AVATAR = 1;

    public Dialog mDialog;
    public TextView positive;
    public TextView negative;

    public TextView mSelectCamera;
    public TextView mSelectPicture;
    public TextView mCancel;
    public View mView;



    public PhotoSelectDialog(Context context, int type) {

        if (PICK_AVATAR == type) {
            LayoutInflater inflater = LayoutInflater.from(context);
            mView = inflater.inflate(R.layout.dialog_pick_avatar, null);
            mDialog = new Dialog(context, R.style.dialog);
            mDialog.setContentView(mView);

            Window dialogWindow = mDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.width = context.getResources().getDisplayMetrics().widthPixels; // 宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
            dialogWindow.setAttributes(lp);

            mDialog.setCanceledOnTouchOutside(true);
            mCancel = mView.findViewById(R.id.select_dismiss);
            mSelectCamera = mView.findViewById(R.id.select_camera);
            mSelectPicture = mView.findViewById(R.id.select_picture);

            //设置点击事件
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}



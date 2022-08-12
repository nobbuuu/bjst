package com.dream.bjst.account.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;

public class PrivacyActivity extends BaseActivity {
    RImageView mUnselectView;
    RTextView privacyTv;
    Button checkButton, disagreeButton;
    //弹窗控件
    PopupWindow popupWindow;
    View pwView;
    boolean isClick = false;

    @Override
    protected int initLayout() {
        //改变状态栏文字的颜色
        new StatusBarUtils().adjustWindow(this, true);
        return R.layout.activity_privacy;
    }

    @Override
    protected void initView() {
        mUnselectView = fvbi(R.id.privacy_unselect_image);
        checkButton = fvbi(R.id.privacy_check_button);
        disagreeButton = fvbi(R.id.privacy_disagree_button);
        //网络协议
        privacyTv = fvbi(R.id.privacy_policy_tv);
    }

    @Override
    protected void initData() {
        //利用H5给网络协议添加超链接
        privacyTv.setText(Html.fromHtml(" Accept " + "<a href=\"http://www.baidu.com\">Terms & Conditions</a>" + " and " + "<a href=\"http://www.baidu.com\">Privacy Policy</a>" + "and to receive notification from SMS and email"), TextView.BufferType.NORMAL);
        privacyTv.setMovementMethod(LinkMovementMethod.getInstance());
        event();
    }

    private void event() {
        //勾选按钮
        mUnselectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick == false) {
                    mUnselectView.setImageResource(R.mipmap.select);
                    isClick = true;
                } else {
                    mUnselectView.setImageResource(R.mipmap.unselect);
                    isClick = false;
                }
            }
        });
        //check按钮
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick == false) {
                    ToastUtils.showShort("Please check this box and continue");
                } else {
                    ToastUtils.showShort("检查中...");
                }
            }
        });
        //disagree
        disagreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //测试弹窗
        privacyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示弹窗
                showPopWindow();
            }
        });
    }

    /**
     * 显示弹窗方法
     */
    private void showPopWindow() {
        // 加载弹窗布局
        pwView = LayoutInflater.from(this).inflate(R.layout.item_popupwindow_privacy, null, false);
        // 实例化 PopupWindow
        popupWindow = new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置关闭popup按钮
        RImageView closeImage=pwView.findViewById(R.id.privacy_fault_image);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                windowAlpha(1f);
            }
        });
        // 设置 popupWindow
        popupWindow.setFocusable(true);// 取得焦点
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        // 加载弹窗动画
        popupWindow.setAnimationStyle(R.style.pw_bottom_anim_style);
       //从底部显示
        popupWindow.showAtLocation(pwView, Gravity.BOTTOM, 0, 20);
        windowAlpha(0.5f);
    // 设置弹窗关闭监听——恢复亮度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            windowAlpha(1f);
        }
    });

    }
    // 控制背景亮度
    private void windowAlpha(float alpha) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = alpha;
        getWindow().setAttributes(attributes);
    }


}
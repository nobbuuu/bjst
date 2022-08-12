package com.dream.bjst.account.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;

public class ChatMessageActivity extends BaseActivity {
    TitleBar mTitleBar;


    @Override
    protected int initLayout() {
        //改变状态栏文字的颜色
        new StatusBarUtils().adjustWindow(this, true);
        return R.layout.activity_chat_message;
    }

    @Override
    protected void initView() {
    mTitleBar=fvbi(R.id.account_chat_title);
    }

    @Override
    protected void initData() {
    mTitleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
    }
}
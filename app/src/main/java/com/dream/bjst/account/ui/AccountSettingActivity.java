package com.dream.bjst.account.ui;

import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.hjq.bar.TitleBar;

public class AccountSettingActivity extends BaseActivity {
    TitleBar mTitleBar;


    @Override
    protected int initLayout() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void initView() {
        mTitleBar=fvbi(R.id.account_my_setting_title);
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
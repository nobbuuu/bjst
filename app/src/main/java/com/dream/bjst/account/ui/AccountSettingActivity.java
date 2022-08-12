package com.dream.bjst.account.ui;

import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.hjq.bar.TitleBar;
import com.ruffian.library.widget.RTextView;

public class AccountSettingActivity extends BaseActivity {
    TitleBar mTitleBar;
    RTextView versionTv;

    @Override
    protected int initLayout() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void initView() {
        mTitleBar = fvbi(R.id.account_my_setting_title);
        versionTv = fvbi(R.id.setting_version);
    }

    @Override
    protected void initData() {
        //获取标题栏的返回按钮
        mTitleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //获取安卓版本号
        versionTv.setText(getVersionCode() + ". 0. 0");

    }
}
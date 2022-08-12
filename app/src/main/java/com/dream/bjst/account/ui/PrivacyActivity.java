package com.dream.bjst.account.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;

public class PrivacyActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        new StatusBarUtils().adjustWindow(this,true);
        return R.layout.activity_privacy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
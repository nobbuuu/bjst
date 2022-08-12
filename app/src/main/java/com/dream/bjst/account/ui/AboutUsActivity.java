package com.dream.bjst.account.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;

public class AboutUsActivity extends BaseActivity {
     TitleBar mAboutUsTitle;

    @Override
    protected int initLayout() {
        //调整状态栏的字体颜色为黑色
        new StatusBarUtils().adjustWindow(this,true);
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
    mAboutUsTitle=fvbi(R.id.about_us_title);
    }

    @Override
    protected void initData() {
      event();
    }

    private void event() {
        mAboutUsTitle.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
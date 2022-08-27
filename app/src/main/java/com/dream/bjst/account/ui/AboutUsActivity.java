package com.dream.bjst.account.ui;

import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.hjq.bar.TitleBar;

public class AboutUsActivity extends BaseActivity {
     TitleBar mAboutUsTitle;

    @Override
    protected int initLayout() {
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
package com.dream.bjst.repayment.ui;


import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;

public class RepaymentDetailActivity extends BaseActivity {
    TitleBar mTitleBar;


    @Override
    protected int initLayout() {
        //调整状态栏的字体颜色为黑色
        new StatusBarUtils().adjustWindow(this,true);
        return R.layout.activity_repayment_detail;
    }

    @Override
    protected void initView() {
        mTitleBar=fvbi(R.id.title_bar);
    }

    @Override
    protected void initData() {
    event();
    }

    private void event() {
        mTitleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
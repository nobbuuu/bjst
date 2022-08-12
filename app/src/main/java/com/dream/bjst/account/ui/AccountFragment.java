package com.dream.bjst.account.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.dream.bjst.R;
import com.dream.bjst.app.MyAppKt;
import com.dream.bjst.base.BaseFragment;
import com.ruffian.library.widget.RImageView;

public class AccountFragment extends BaseFragment {
    RImageView settingImage;
    LinearLayout aboutUsLayout;
    @Override
    protected int setLayout() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        settingImage=fvbi(R.id.account_setting);
        aboutUsLayout=fvbi(R.id.account_about_us);
    }

    @Override
    protected void initData() {
        //设置页面
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(),AccountSettingActivity.class));
            }
        });
        //关于我们
        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MyAppKt.getMApplication(),AboutUsActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

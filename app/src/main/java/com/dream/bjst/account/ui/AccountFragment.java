package com.dream.bjst.account.ui;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dream.bjst.R;
import com.dream.bjst.app.MyAppKt;
import com.dream.bjst.base.BaseFragment;
import com.ruffian.library.widget.RImageView;

public class AccountFragment extends BaseFragment {
    RImageView settingImage;
    @Override
    protected int setLayout() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        settingImage=fvbi(R.id.account_setting);
    }

    @Override
    protected void initData() {
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("点击设置");
                startActivity(new Intent(MyAppKt.getMApplication(),AccountSettingActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

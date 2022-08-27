package com.dream.bjst.account.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.didichuxing.doraemonkit.util.ToastUtils;
import com.dream.bjst.R;
import com.dream.bjst.app.MyAppKt;
import com.dream.bjst.base.BaseFragment;
import com.dream.bjst.loan.ui.LoanRecordsActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.ruffian.library.widget.RImageView;

public class AccountFragment extends BaseFragment {
    RImageView settingImage;
    LinearLayout aboutUsLayout, deleteDataLayout, privacyLayout, chatServiceLayout, loanRecordLayout, identificationLayout;
    private ConstraintLayout topLay;

    @Override
    protected int setLayout() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        settingImage = fvbi(R.id.account_setting);
        aboutUsLayout = fvbi(R.id.account_about_us);
        deleteDataLayout = fvbi(R.id.account_delete_individual_data);
        privacyLayout = fvbi(R.id.account_privacy_police);
        chatServiceLayout = fvbi(R.id.account_customer_service);
        loanRecordLayout = fvbi(R.id.account_loan_record);
        identificationLayout = fvbi(R.id.account_re_enter_rv);
        topLay = fvbi(R.id.topLay);
        StatusBarUtils.adjustWindow(requireActivity(),R.color.color_F8FFF0, topLay);
    }

    @Override
    protected void initData() {
        //设置页面
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(), AccountSettingActivity.class));
            }
        });
        //关于我们
        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(), AboutUsActivity.class));
            }
        });
        //删除数据
        deleteDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(), AccountDeleteActivity.class));

            }
        });
        //隐私界面
        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(), PrivacyActivity.class));
            }
        });
        //顾客聊天服务
        chatServiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(), ChatMessageActivity.class));
            }
        });
        //进入贷款记录界面
        loanRecordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppKt.getMApplication(), LoanRecordsActivity.class));
            }
        });
        //进入认证界面
        identificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MyAppKt.getMApplication(), LoanRecordsActivity.class));
                ToastUtils.showLong("认证界面还没有完善");

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}

package com.dream.bjst.repayment.ui;


import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;

public class RepaymentDetailActivity extends BaseActivity {
    TitleBar mTitleBar;
    REditText mDigitalEt;
    RTextView notionTv;
    Button mPayButton;
    @Override
    protected int initLayout() {
        //调整状态栏的字体颜色为黑色
        new StatusBarUtils().adjustWindow(this, true);
        return R.layout.activity_repayment_detail;
    }

    @Override
    protected void initView() {
        mTitleBar = fvbi(R.id.title_bar);
        mDigitalEt = fvbi(R.id.digital_et);
        notionTv = fvbi(R.id.notion_tv);
        mPayButton=fvbi(R.id.confirm_pay_button);
    }

    @Override
    protected void initData() {
        event();
        String inputContent = mDigitalEt.getText().toString();
        mDigitalEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputContent == null) {
                    notionTv.setVisibility(View.VISIBLE);
                    notionTv.setText("Pls enter UTR No.");
                } else if (inputContent.length() < 12) {
                    notionTv.setVisibility(View.VISIBLE);
                    notionTv.setText("Enter the correct No.");
                }
                return;
            }
        });
    }

    private void event() {
        mTitleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //确定提交按钮
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent());
            }
        });
    }
}
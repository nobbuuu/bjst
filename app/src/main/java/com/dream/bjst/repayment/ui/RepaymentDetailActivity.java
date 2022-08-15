package com.dream.bjst.repayment.ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
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
    Button mPayButton,mRepaidSubmitButton;
    String inputContent;
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
        mRepaidSubmitButton=fvbi(R.id.repaid_submit_button);
    }

    @Override
    protected void initData() {
        event();

        inputContent = mDigitalEt.getText().toString();
        mDigitalEt.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int startEdit;
            int endEdit;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
//                if (count<12){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setText("Enter the correct No.");
//                    ToastUtils.showShort(count);
//                }else if (count==12){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setText("Enter is correct！");
//                    notionTv.setTextColor(R.color.green_color_background);
//                }else return;
            }


            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                startEdit = mDigitalEt.getSelectionStart();
                endEdit = mDigitalEt.getSelectionEnd();
                //mNumTextView.setText(String.valueOf(temp.length()));
                if (temp.length() > 12) {
                    s.delete(startEdit - 1, endEdit);
                    int tempSelection = startEdit;
                    mDigitalEt.setText(s);
                    mDigitalEt.setSelection(tempSelection);
                    ToastUtils.showShort("你输入的字已经超过了！");
                } else if (temp.length()<12){
                    notionTv.setVisibility(View.VISIBLE);
                    notionTv.setText("Enter the correct No.");
                }else {
                    notionTv.setVisibility(View.VISIBLE);
                    notionTv.setText("Enter is correct！");
                    notionTv.setTextColor(R.color.green_color_background);
                }


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
                startActivity(new Intent(RepaymentDetailActivity.this,ExtendRePaymentActivity.class));
            }
        });
        //repaidButton
        mRepaidSubmitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
//                if (inputContent==null){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setTextColor(R.color.red_profit);
//                    notionTv.setText("Enter the is null.");
//                }else if (inputContent.length()<12){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setText("Enter the correct No.");
//                    notionTv.setTextColor(R.color.red_profit);
//                    //notionTv.setTextColor(R.color.green_color_background);
//                }else if (inputContent.length()==12){
//                    notionTv.setVisibility(View.VISIBLE);
//                    notionTv.setText("Enter is correct！");
//                    notionTv.setTextColor(R.color.green_color_background);
//                }
            }
        });
    }
}
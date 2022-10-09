package com.dream.bjst.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 创建日期：2022-06-30 on 1:34
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
public class VerifyCodeTimeDownUtil extends CountDownTimer {

    private TextView btGetVerifyCode;

    public VerifyCodeTimeDownUtil(long millisInFuture, long countDownInterval, TextView btGetVerifyCode) {
        super(millisInFuture, countDownInterval);
        this.btGetVerifyCode = btGetVerifyCode;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btGetVerifyCode.setText(millisUntilFinished / 1000 + "S");
    }

    @Override
    public void onFinish() {
        btGetVerifyCode.setText("重新获取验证码");
        btGetVerifyCode.setClickable(true);
    }

    public void startNow() {
        btGetVerifyCode.setClickable(false);
        start();
    }
}

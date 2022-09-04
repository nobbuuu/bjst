//package com.dream.bjst.repayment.ui;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.dream.bjst.R;
//import com.dream.bjst.base.BaseActivity;
//import com.dream.bjst.utils.StatusBarUtils;
//import com.hjq.bar.TitleBar;
//
//public class  ExtendRePaymentActivity extends BaseActivity {
//     TitleBar titleBar;
//
//    @Override
//    protected int initLayout() {
//        //调整状态栏的字体颜色为黑色
//        StatusBarUtils.adjustWindow(this, true);
//        return R.layout.activity_extend_re_payment;
//    }
//
//    @Override
//    protected void initView() {
//    titleBar=fvbi(R.id.title_bar);
//    }
//
//    @Override
//    protected void initData() {
//     titleBar.getLeftView().setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//             onBackPressed();
//         }
//     });
//    }
//}
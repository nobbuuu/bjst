package com.dream.bjst.account.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.dream.bjst.R;
import com.dream.bjst.base.BaseActivity;
import com.dream.bjst.utils.StatusBarUtils;
import com.hjq.bar.TitleBar;
import com.ruffian.library.widget.RTextView;

public class AccountDeleteActivity extends BaseActivity {

    TitleBar deleteTitle;
    RTextView deleteContent;

    @Override
    protected int initLayout() {
        //调整状态栏的字体颜色为黑色
        new StatusBarUtils().adjustWindow(this,true);
        return R.layout.activity_account_delete;
    }

    @Override
    protected void initView() {
    deleteTitle=fvbi(R.id.account_delete_title);
    deleteContent=fvbi(R.id.delete_content);
    }

    @Override
    protected void initData() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               startActivity(new Intent(AccountDeleteActivity.this,DeleteProgressActivity.class));

           }
       }).start();
    event();
    }

    private void event() {
        deleteTitle.getLeftView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //点击文案删除数据
        deleteContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountDeleteActivity.this,DeleteProgressActivity.class));
            }
        });
    }
}
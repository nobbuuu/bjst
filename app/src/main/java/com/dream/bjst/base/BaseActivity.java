package com.dream.bjst.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        // 设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState, persistentState);
        setContentView(setLayout());
        setView();
        setData();
    }

    /**
     * 绑定布局
     * @return
     */
    protected abstract int  setLayout();

    /**
     * 初始化组件
     */
    protected abstract void setView();

    /**
     * 设置数据等逻辑代码
     */
    protected abstract void setData();

    /**
     * 简化findViewById()
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T fvbi(int resId){
        return (T) findViewById(resId);
    }

    /**
     * Intent跳转
     * @param context
     * @param clazz
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz){
        toClass(context,clazz,null);
    }

    /**
     * Intent带值跳转
     * @param context
     * @param clazz
     * @param bundle
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz, Bundle bundle){
        Intent intent = new Intent(context,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 带返回值的跳转
     * @param context
     * @param clazz
     * @param bundle
     * @param reuqestCode
     */
    protected void toClass(Context context,Class<? extends BaseActivity> clazz,Bundle bundle,int reuqestCode){
        Intent intent = new Intent(context,clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent,reuqestCode);
    }
}

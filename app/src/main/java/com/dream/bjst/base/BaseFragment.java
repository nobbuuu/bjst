package com.dream.bjst.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment  extends Fragment implements View.OnClickListener {
    protected Context mContext;

    /**
     * 当fragment与activity发生关联时调用
     * @param context  与之相关联的activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(),null);
        return view;
    }

    /**
     * 绑定布局
     * @return
     */
    protected abstract int setLayout();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 设置数据等逻辑代码
     */
    protected abstract void initData();

    /**
     * 简化findViewById
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T fvbi(int resId){
        return (T) getView().findViewById(resId);
    }

    /**
     * intent跳转
     * @param context
     * @param clazz
     */
    protected void toClass(Context context,Class<? extends BaseActivity> clazz){
        toClass(context,clazz,null);
    }

    /**
     * intent带值跳转
     * @param context
     * @param clazz
     * @param bundle
     */
    protected void toClass(Context context,Class<? extends BaseActivity> clazz,Bundle bundle){
        Intent intent = new Intent(context,clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 带返回值的跳转
     * @param context
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    protected void toClass(Context context,Class<? extends BaseActivity> clazz,Bundle bundle,int requestCode){
        Intent intent = new Intent(context,clazz);
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent,requestCode);
    }
}

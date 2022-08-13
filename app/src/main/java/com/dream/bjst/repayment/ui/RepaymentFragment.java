package com.dream.bjst.repayment.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.dream.bjst.R;
import com.dream.bjst.app.MyAppKt;
import com.dream.bjst.base.BaseFragment;
import com.dream.bjst.repayment.adapter.EmptyAdapter;

import java.util.ArrayList;
import java.util.List;

public class RepaymentFragment extends BaseFragment {
    RecyclerView mRecyclerView;
    EmptyAdapter mEmptyAdapter;
    List<String> mList;
    @Override
    protected int setLayout() {
        return R.layout.fragment_repayment;
    }

    @Override
    protected void initView() {
     mRecyclerView=fvbi(R.id.repayment_rv_plan);
    }

    @Override
    protected void initData() {
        mList=new ArrayList<>();
        getData();
        mEmptyAdapter= new EmptyAdapter(MyAppKt.getMApplication(),mList);
       // 显示空布局
        mEmptyAdapter.showEmptyView(true);
        //rvList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEmptyAdapter);
//        mRecyclerView.setBackgroundResource(R.mipmap.ic_launcher);

    }

    private void getData() {
        for (int i = 0; i <10 ; i++) {
            mList.add("222222");
        }
    }

    @Override
    public void onClick(View v) {

    }
}

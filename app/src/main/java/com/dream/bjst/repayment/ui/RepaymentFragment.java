package com.dream.bjst.repayment.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.dream.bjst.R;
import com.dream.bjst.app.MyAppKt;
import com.dream.bjst.base.BaseFragment;
import com.dream.bjst.bean.OverduedBean;
import com.dream.bjst.bean.RepaymentBean;
import com.dream.bjst.repayment.adapter.EmptyAdapter;
import com.dream.bjst.repayment.adapter.RePaymentAdapter;
import com.dream.bjst.repayment.adapter.RePaymentInAdapter;
import com.dream.bjst.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class RepaymentFragment extends BaseFragment {
    RecyclerView mRecyclerView;
    private RePaymentAdapter mRePaymentAdapter;
    private RePaymentInAdapter mRePaymentInAdapter;
    private List<RepaymentBean> mList;

    @Override
    protected int setLayout() {
        //调整状态栏的字体颜色为黑色
        new StatusBarUtils().adjustWindow(getActivity(), true);
        return R.layout.fragment_repayment;
    }

    @Override
    protected void initView() {
        mRecyclerView = fvbi(R.id.repayment_rv_plan);
    }

    @Override
    protected void initData() {
        mRePaymentAdapter = new RePaymentAdapter();
        mRePaymentInAdapter=new RePaymentInAdapter();
        // 显示空布局
        mRePaymentAdapter.setEmptyView(getEmptyView());
        mRecyclerView.setAdapter(mRePaymentAdapter);
        setData();
    }

    private void setData() {
        mList = new ArrayList<>();
        List<OverduedBean> inList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            inList.add(new OverduedBean("Overdued"));
        }
        for (int i = 0; i < 10; i++) {
            mList.add(new RepaymentBean(inList));
        }
        mRePaymentAdapter.setList(mList);
        mRePaymentInAdapter.setList(inList);

        //点击时间
        event();
    }

    private void event() {
        mRePaymentInAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                startActivity(new Intent(MyAppKt.getMApplication(),RepaymentDetailActivity.class));
            }
        });

        mRePaymentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(MyAppKt.getMApplication(),RepaymentDetailActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取空布局
     */
    private View getEmptyView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repayment_empty, null, false);
        Button btnLoadData = view.findViewById(R.id.btn_load_data);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.repayment_to_navigation_loan);
            }
        });
        return view;
    }
}

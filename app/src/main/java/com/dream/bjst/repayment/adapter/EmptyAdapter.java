package com.dream.bjst.repayment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dream.bjst.R;
import com.dream.bjst.app.MyAppKt;
import com.dream.bjst.loan.ui.LoanFragment;
import com.dream.bjst.main.MainActivity;

import java.util.List;

/**
 * 创建日期：2022-08-14 on 0:30
 * 描述:衣带渐宽终不悔、为伊消得人憔悴
 * 作者:HeGuiCun Administrator
 */
public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.ViewHolder> {

    // 普通的item ViewType
    private static final int TYPE_ITEM = 1;
    // 空布局的ViewType
    private static final int TYPE_EMPTY = 2;
    private Context mContext;

    // 数据
    private List<String> mList;

    // 是否显示空布局，默认不显示
    private boolean showEmptyView = false;

    public EmptyAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent.setBackgroundResource(R.mipmap.ic_launcher);
        if (viewType == TYPE_EMPTY) {
            // 创建空布局item
            return new ViewHolder(getEmptyView(parent));
        } else {
            // 创建普通的item
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_repayment_data, parent, false);
            return new ViewHolder(view);
        }
    }

    /**
     * 获取空布局
     */
    private View getEmptyView(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repayment_empty, parent, false);
        Button btnLoadData = view.findViewById(R.id.btn_load_data);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAppKt.getMApplication(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//为待启动的Activity指定FLAG_ACTIVITY_NEW_TASK标记位。
                MyAppKt.getMApplication().startActivity(intent);//使用ApplicationContext开启便准模式下的axtivity会出现错误
            }
        });
        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 如果是空布局item，不需要绑定数据
        if (!isEmptyPosition(position)) {
            holder.itemView.setBackgroundResource(R.mipmap.ic_launcher);
            holder.tvItem.setText(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        // 判断数据是否空，如果没有数据，并且需要显示空布局，就返回1。
        int count = mList != null ? mList.size() : 0;
        if (count > 0) {
            return count;
        } else if (showEmptyView) {
            // 显示空布局
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmptyPosition(position)) {
            //空布局
            return TYPE_EMPTY;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setList(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    /**
     * 判断是否是空布局
     */
    public boolean isEmptyPosition(int position) {
        int count = mList != null ? mList.size() : 0;
        return position == 0 && showEmptyView && count == 0;
    }

    /**
     * 设置空布局显示。默认不显示
     */
    public void showEmptyView(boolean isShow) {
        if (isShow != showEmptyView) {
            showEmptyView = isShow;
            notifyDataSetChanged();
        }
    }

    public boolean isShowEmptyView() {
        return showEmptyView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }

}


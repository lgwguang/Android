package com.lgw.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgw.R;
import com.lgw.entity.RightBean;

import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder> {
    private final List<RightBean.DataBean> rightlist;
    private final Context mContext;

    public RightAdapter(Context mContext) {

        this.mContext = mContext;
        rightlist = new ArrayList<>();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.right, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(rightlist.get(i).getName());
        List<RightBean.DataBean.ListBean> list = rightlist.get(i).getList();

        RightAdapter1 rightAdapter1 = new RightAdapter1(list, mContext);
        viewHolder.recycle_right1.setAdapter(rightAdapter1);

        viewHolder.recycle_right1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        rightAdapter1.setOnItemClickListener(new RightAdapter1.OnItemClickListener() {
            @Override
            public void onItemClickListener(int layoutPosition) {
                //点击条目的时候要根据点击的下标跳转到相应的页面，
                // 所以需要拿到解析完的集合list.get(传过来的下标).地址
//                Intent intent = new Intent(mContext, Goods.class);
//                intent.putExtra("pscid",pscid);
//                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return rightlist.size();
    }

    public void setData(List<RightBean.DataBean> dataBeans) {
        this.rightlist.clear();
        rightlist.addAll(dataBeans);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recycle_right1;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recycle_right1 = itemView.findViewById(R.id.recycle_right1);
            name = itemView.findViewById(R.id.name);
        }
    }
}
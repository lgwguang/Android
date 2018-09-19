package com.lgw.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgw.R;
import com.lgw.Utils.Util;
import com.lgw.bean.MenuItem;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<MenuItem> mDatas;

    public HomeAdapter(List<MenuItem> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = mDatas.get(position);
        holder.ivicon.setImageResource(Util.getResId(item.getActionImage(),R.drawable.class));
        holder.tvfunction.setText(item.getActionName());
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onViewItemListener!=null){
                    onViewItemListener.viewItemClick(holder.getLayoutPosition(),item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivicon;
        TextView tvfunction;
        LinearLayout ll_item;
        public ViewHolder(View itemView) {
            super(itemView);
            ivicon = itemView.findViewById(R.id.iv_icon);
            tvfunction = itemView.findViewById(R.id.tv_function);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }

    public OnViewItemListener onViewItemListener;

    public void setGridViewItemListener(OnViewItemListener onViewItemListener){
        this.onViewItemListener = onViewItemListener;
    }
}

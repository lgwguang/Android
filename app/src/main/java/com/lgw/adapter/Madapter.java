package com.lgw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgw.R;
import com.lgw.entity.GroupBean;

import java.util.ArrayList;
import java.util.List;

public class Madapter extends RecyclerView.Adapter<Madapter.ViewHolder> {
    private final Context mContext;
    private final List<GroupBean.DataBean> grouplist;

    public Madapter(Context mContext) {
        this.mContext=mContext;
        grouplist = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.group, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv.setText(grouplist.get(i).getName());

        final GroupBean.DataBean bean = grouplist.get(i);
        //条目点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMadapterListener.setonitemclick(bean.getCid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return grouplist.size();
    }

    public void setData(List<GroupBean.DataBean> dataBeans) {
        grouplist.clear();
        grouplist.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface  MadapterListener{
        void setonitemclick(int cid);
    }

    public MadapterListener mMadapterListener;

    public void setMadapterListener(MadapterListener madapterListener){
        mMadapterListener=madapterListener;
    }
}

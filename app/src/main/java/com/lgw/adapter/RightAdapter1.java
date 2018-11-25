package com.lgw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgw.R;
import com.lgw.Utils.GlideImageLoader;
import com.lgw.entity.RightBean;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class RightAdapter1 extends RecyclerView.Adapter<RightAdapter1.ViewHolder> {
    private final List<RightBean.DataBean.ListBean> list;
    private final Context mContext;
    public RightAdapter1(List<RightBean.DataBean.ListBean> list, Context mContext) {
        this.list=list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.right1, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.name.setText(list.get(i).getName());
//        ImageLoader.getInstance().displayImage(list.get(i).getIcon(),
//                viewHolder.image,ImageLoderConfigs.getimageoption(mContext));
        Glide.with(mContext).load(list.get(i).getIcon()).into(viewHolder.image);
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(list.get(i).getPscid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }

    //实现接口
    private OnItemClickListener mOnItemClickListener;
    //创建接口
    public interface OnItemClickListener{
        void onItemClickListener(int layoutPosition);
    }
    //外界调用的方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener= onItemClickListener;
    }

}

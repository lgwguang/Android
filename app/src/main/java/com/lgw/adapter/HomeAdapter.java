package com.lgw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgw.R;
import com.lgw.Utils.Util;
import com.lgw.bean.Home;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {

    ArrayList<Home> data;
    Context mContext;

    public HomeAdapter(Context context,ArrayList<Home> data) {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data!=null?data.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_home,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Home home = data.get(position);
        viewHolder.tvFunction.setText(home.getActionName());
        viewHolder.ivIcon.setImageResource(Util.getResId(home.getActionImage(),R.drawable.class));
        viewHolder.llItem.setOnClickListener(v -> {
            if(onViewItemListener!=null){
                onViewItemListener.viewItemClick(position,home);
            }
        });

        return convertView;
    }

    public OnViewItemListener onViewItemListener;

    public void setGridViewItemListener(OnViewItemListener onViewItemListener){
        this.onViewItemListener = onViewItemListener;
    }

    static class  ViewHolder{
        private TextView tvFunction;
        private ImageView ivIcon;
        private LinearLayout llItem;
        public ViewHolder(View convertView) {
            tvFunction = convertView.findViewById(R.id.tv_function);
            ivIcon = convertView.findViewById(R.id.iv_icon);
            llItem = convertView.findViewById(R.id.ll_item);
        }
    }
}

package com.lgw.adapter;

import android.content.Context;

import com.lgw.R;
import com.lgw.Utils.Util;
import com.lgw.bean.MenuItem;
import com.lgw.bean.ViewHolder;

import java.util.List;

public class MenuAdapter extends CommonAdapter<MenuItem> {

    public MenuAdapter(Context context, List<MenuItem> mDatas) {
        this(context, mDatas, R.layout.item_home);
    }

    public MenuAdapter(Context context, List<MenuItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MenuItem item) {
        holder.setText(R.id.tv_function,item.getActionName());
        holder.setImageResource(R.id.iv_icon, Util.getResId(item.getActionImage(),R.drawable.class));
        holder.getView(R.id.ll_item).setOnClickListener(v -> {
                if(onViewItemListener!=null){
                    onViewItemListener.viewItemClick(holder.getPosition(),item);
                }
        });
    }
    public OnViewItemListener onViewItemListener;

    public void setGridViewItemListener(OnViewItemListener onViewItemListener){
        this.onViewItemListener = onViewItemListener;
    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
//        if(convertView == null){
//            convertView = View.inflate(mContext, R.layout.item_home,null);
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        Home home = data.get(position);
//        viewHolder.tvFunction.setText(home.getActionName());
//        viewHolder.ivIcon.setImageResource(Util.getResId(home.getActionImage(),R.drawable.class));
//        viewHolder.llItem.setOnClickListener(v -> {
//            if(onViewItemListener!=null){
//                onViewItemListener.viewItemClick(position,home);
//            }
//        });
//
//        return convertView;
//    }
//
//
//    static class  ViewHolder{
//        private TextView tvFunction;
//        private ImageView ivIcon;
//        private LinearLayout llItem;
//        public ViewHolder(View convertView) {
//            tvFunction = convertView.findViewById(R.id.tv_function);
//            ivIcon = convertView.findViewById(R.id.iv_icon);
//            llItem = convertView.findViewById(R.id.ll_item);
//        }
//    }
}

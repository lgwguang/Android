package com.lgw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lgw.R;
import com.lgw.Utils.GlideImageLoader;
import com.lgw.entity.ResultBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter {
    /**
     * 数据Bean对象
     */
    private ResultBean resultBean;

    private static final int BANNER = 0;

    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    /**
     * 当前类型
     */
    private int currentType = BANNER;
    private final LayoutInflater mLayoutInflater;
    private Context mContext;

    public OtherAdapter(Context mContext,ResultBean resultBean){
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == BANNER){
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager,null),resultBean);
        }else if(viewType == CHANNEL){
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null));
        }else if(viewType == ACT){

        }else if(viewType == SECKILL){

        }else if(viewType == RECOMMEND){

        }else if(viewType == HOT){

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        }else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    class BannerViewHolder extends RecyclerView.ViewHolder{
        public Banner banner;
        public BannerViewHolder(View itemView,ResultBean resultBean) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        }

        public void setData(List<ResultBean.BannerInfoBean> banner_info){
            List<String> imageUris = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                imageUris.add(banner_info.get(i).getImagePath());
                titles.add(banner_info.get(i).getTitle());
            }
            banner.setImages(imageUris);
            banner.setBannerTitles(titles);
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    ToastUtils.showShort(banner_info.get(position).getUrl());
                }
            });
        }
    }
    class ChannelViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerview;
        public ChannelViewHolder(View itemView) {
            super(itemView);
            recyclerview = itemView.findViewById(R.id.recyclerview);
        }

        public void setData(List<ResultBean.MenuItem> getChannel_info){
            recyclerview.setLayoutManager(new GridLayoutManager(mContext,4));
            HomeAdapter homeAdapter = new HomeAdapter(getChannel_info);
            recyclerview.setAdapter(homeAdapter);
        }
    }
}

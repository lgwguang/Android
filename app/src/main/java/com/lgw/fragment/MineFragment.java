package com.lgw.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.lgw.R;
import com.lgw.adapter.Madapter;
import com.lgw.adapter.RightAdapter;
import com.lgw.entity.GroupBean;
import com.lgw.entity.RightBean;
import com.lgw.presenter.GroupPresenter;
import com.lgw.presenter.RightPresenter;

import java.util.List;


public class MineFragment extends Fragment implements GroupPresenter.GroupListener, RightPresenter.RightListener {

    private static MineFragment mineFragment;

    public static MineFragment getInstance(){
        if(mineFragment == null){
            synchronized(MineFragment.class){
                if(mineFragment == null){
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }

    private View rootView;
    RecyclerView mRecyclerView,recycle_right;
    Context mContext;
    Madapter madapter;
    RightAdapter rightAdapter;
    RightPresenter rightPresenter;
    GroupPresenter groupPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_mine,container,false);
            initView(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mRecyclerView  = rootView.findViewById(R.id.recycle);
        recycle_right = rootView.findViewById(R.id.recycle_right);
        initLeft();
        initRight("http://www.zhaoapi.cn/product/getProductCatagory?cid=1");
    }
    private void initRight(String urls) {

        //p层
        rightPresenter = new RightPresenter(this);
        rightPresenter.loadData(urls);
        //适配器
        rightAdapter = new RightAdapter(getActivity());
        recycle_right.setAdapter(rightAdapter);

        recycle_right.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

    }
    private void initLeft() {
        //左边布局  适配器
        madapter = new Madapter(mContext);
        mRecyclerView.setAdapter(madapter);

        //p层  请求数据
        groupPresenter = new GroupPresenter(this);
        groupPresenter.requestData();

        //布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        //条目点击
        madapter.setMadapterListener(new Madapter.MadapterListener() {
            @Override
            public void setonitemclick(int cid) {

                LogUtils.e("哈哈哈哈哈哈哈哈哈哈",cid+"");
                String path =  "http://www.zhaoapi.cn/product/getProductCatagory?cid="+cid;
                initRight(path);

            }
        });
    }

    @Override
    public void datasuccess(List<GroupBean.DataBean> dataBeans) {
        madapter.setData(dataBeans);
    }

    @Override
    public void rightsuccess(List<RightBean.DataBean> dataBeans) {
        rightAdapter.setData(dataBeans);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        rightPresenter = null;
        groupPresenter=null;

    }
}

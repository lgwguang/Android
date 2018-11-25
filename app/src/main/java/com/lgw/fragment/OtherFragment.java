package com.lgw.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgw.R;
import com.lgw.Utils.OkGoUtil;
import com.lgw.activity.Main2Activity;
import com.lgw.adapter.OtherAdapter;
import com.lgw.bean.BaseResponse;
import com.lgw.callback.DialogCallback;
import com.lgw.entity.ResultBean;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OtherFragment extends Fragment {


    private static OtherFragment otherFragment;
    private RecyclerView rv;
    private OtherAdapter otherAdapter;

    public static OtherFragment getInstance(){
        if(otherFragment == null){
            synchronized(OtherFragment.class){
                if(otherFragment == null){
                    otherFragment = new OtherFragment();
                }
            }
        }
        return otherFragment;
    }
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* View view = inflater.inflate(R.layout.fragment_other,container,false);
        initView(view);
        return view;*/
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_other,container,false);
            initView(rootView);
            initdata();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public void initView(View view){
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public void initdata(){

        OkGoUtil.getRequets("http://www.wanandroid.com/banner/json", this, null, new DialogCallback<BaseResponse<List<ResultBean.BannerInfoBean>>>((Main2Activity)getActivity()) {

            @Override
            public void onSuccess(Response<BaseResponse<List<ResultBean.BannerInfoBean>>> response) {
                try {
                    BaseResponse<List<ResultBean.BannerInfoBean>> body = response.body();
                    List<ResultBean.BannerInfoBean> data = body.getData();
                    for (ResultBean.BannerInfoBean datum : data) {
                        String imagePath = datum.getImagePath();
                    }
                    ResultBean resultBean = new ResultBean();
                    resultBean.setBanner_info(data);
                    String menu = ResourceUtils.readAssets2String("menudata.json","UTF-8");
                    JSONObject jsonObject = new JSONObject(menu);
                    JSONArray jsonArray = jsonObject.optJSONArray("MoreDisplayList");
                    ArrayList<ResultBean.MenuItem> listData = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<ResultBean.MenuItem>>() {
                    }.getType());
                    resultBean.setChannel_info(listData);
                    otherAdapter = new OtherAdapter(getContext(),resultBean);
                    rv.setAdapter(otherAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

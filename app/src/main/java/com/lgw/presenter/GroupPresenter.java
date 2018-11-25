package com.lgw.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.lgw.Utils.OkGoUtil;
import com.lgw.callback.DialogCallback;
import com.lgw.callback.JsonCallback;
import com.lgw.entity.GroupBean;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

public class GroupPresenter {

    public  void requestData(){
        String path =  "http://www.zhaoapi.cn/product/getCatagory";
        OkGoUtil.getRequets(path,null,null,new StringCallback(){
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.d(response.toString());
                GroupBean groupBean = new Gson().fromJson(response.body().toString(), GroupBean.class);
                mGroupListener.datasuccess(groupBean.getData());
            }

           /* @Override
            public void onSuccess(Response response) {
                GroupBean groupBean = new Gson().fromJson(response, GroupBean.class);
                mGroupListener.datasuccess(groupBean.getData());
            }*/
        });
        /*OkHttpUtils.getInstance().doGet(path, new OkHttpUtils.Utils() {
            @Override
            public void failed() {

            }

            @Override
            public void success(String string) {
                GroupBean groupBean = new Gson().fromJson(string, GroupBean.class);
                mGroupListener.datasuccess(groupBean.getData());
            }
        });*/
    }

    public interface GroupListener{
        void datasuccess(List<GroupBean.DataBean> dataBeans);
    }

    public GroupListener mGroupListener;

    public GroupPresenter(GroupListener groupListener) {
        mGroupListener = groupListener;
    }
}

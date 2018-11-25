package com.lgw.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.lgw.Utils.OkGoUtil;
import com.lgw.callback.JsonCallback;
import com.lgw.entity.RightBean;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.List;

public class RightPresenter {

    public void loadData(String urls){

        /*OkHttpUtils.getInstance().doGet(urls, new OkHttpUtils.Utils() {
            @Override
            public void failed() {

            }

            @Override
            public void success(String string) {
                //解析
                RightBean rightBean = new Gson().fromJson(string, RightBean.class);
                List<RightBean.DataBean> data = rightBean.getData();
                LogUtils.e("gsaga",rightBean+"");

                mRightListener.rightsuccess(data);
            }
        });*/
        OkGoUtil.getRequets(urls,null,null,new StringCallback(){

            @Override
            public void onSuccess(Response<String> response) {

                RightBean groupBean = new Gson().fromJson(response.body().toString(), RightBean.class);
                List<RightBean.DataBean> data = groupBean.getData();
                LogUtils.e("gsaga"+ groupBean+"");

                mRightListener.rightsuccess(data);
            }
        });


    }

    public interface  RightListener{
        void rightsuccess(List<RightBean.DataBean> dataBeans);
    }
    public RightListener mRightListener;

    public RightPresenter(RightListener rightListener) {
        mRightListener = rightListener;
    }
}

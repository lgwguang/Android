package com.lgw.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lgw.session.SessionInterface;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;

public abstract class BaseActivity extends CheckPermissionsActivity {

    protected Context mContext;
    protected Activity mActivity;
    public SessionInterface sessionInterface;

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        //避免切换横竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = getApplicationContext();
        mActivity = this;
        initView();
        initListener();
        initData();
        sessionInterface = sessionInterface==null?new SessionInterface(this):sessionInterface;
    }


    /**
     * 返回一个用于显示界面的布局id
     * @return          视图id
     */
    public abstract int getContentView();

    /** 初始化View的代码写在这个方法中 */
    public abstract void initView();

    /** 初始化监听器的代码写在这个方法中 */
    public abstract void initListener();

    /** 初始数据的代码写在这个方法中，用于从服务器获取数据 */
    public void initData(){};

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public <T> void handleError(Response<T> response) {
        if (response == null) return;
        if (response.getException() != null){
            response.getException().printStackTrace();
            Throwable exception = response.getException();
            if(exception instanceof UnknownHostException || exception instanceof ConnectException){
                ToastUtils.showShort("网络连接失败，请连接网络！");
            }else if (exception instanceof SocketTimeoutException) {
                ToastUtils.showShort("网络请求超时");
            }else if(exception instanceof HttpException){
                ToastUtils.showShort("服务端响应码404和500了，联系客服吧");
            }
        }
        StringBuilder sb;
        Call call = response.getRawCall();
        if (call != null) {
            LogUtils.dTag("请求失败  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
            LogUtils.d(sb.toString());
        } else {
            LogUtils.d("--");
            LogUtils.d("--");
        }

        LogUtils.d("--");
        okhttp3.Response rawResponse = response.getRawResponse();
        if (rawResponse != null) {
            Headers responseHeadersString = rawResponse.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("stateCode ： ").append(rawResponse.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
            LogUtils.d(sb.toString());
        } else {
            LogUtils.d("--");
        }
    }
}

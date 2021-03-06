package com.lgw.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jude.beam.bijection.BeamAppCompatActivity;
import com.jude.beam.bijection.Presenter;
import com.lgw.session.SessionInterface;

public abstract class NewBaseActivity<PresenterType extends Presenter> extends BeamAppCompatActivity {

    protected Context mContext;
    protected Activity mActivity;
    public SessionInterface sessionInterface;
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

}

package com.lgw.mvp;

import android.os.Bundle;

import com.lgw.R;

public class MvpMainActivity extends BaseMvpActivity<MainContract.IMainPresenter> implements MainContract.IMainView {

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        presenter.requestTestContent();
    }


    @Override
    public void initListener() {

    }

    @Override
    protected MainContract.IMainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void setTestContent() {

    }
}

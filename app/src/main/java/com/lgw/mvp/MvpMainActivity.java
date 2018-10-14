package com.lgw.mvp;

import android.os.Bundle;

import com.lgw.R;

public class MvpMainActivity extends BaseMvpActivity<MainContract.IMainPresenter> implements MainContract.IMainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.requestTestContent();
    }

    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    public void initView() {

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

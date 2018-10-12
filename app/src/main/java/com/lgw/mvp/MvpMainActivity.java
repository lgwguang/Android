package com.lgw.mvp;

import android.os.Bundle;

public class MvpMainActivity extends BaseMvpActivity<MainContract.IMainPresenter> implements MainContract.IMainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.requestTestContent();
    }

    @Override
    protected MainContract.IMainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void setTestContent() {

    }
}

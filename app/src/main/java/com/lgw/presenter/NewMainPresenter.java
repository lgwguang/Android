package com.lgw.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jude.beam.bijection.Presenter;
import com.lgw.activity.NewMainActivity;

public class NewMainPresenter extends Presenter<NewMainActivity> {

    @Override
    protected void onCreate(@NonNull NewMainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }


    @Override
    protected void onCreateView(@NonNull NewMainActivity view) {
        super.onCreateView(view);

        getView().initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

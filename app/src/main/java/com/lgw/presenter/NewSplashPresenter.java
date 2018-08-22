package com.lgw.presenter;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.Presenter;
import com.lgw.Utils.RxUtil;
import com.lgw.activity.NewSplashActivity;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class NewSplashPresenter extends Presenter<NewSplashActivity> {

    private static final int COUNT_DOWN_TIME = 2000;

    private Subscription rxSubscription;

    @Override
    protected void onCreateView(@NonNull NewSplashActivity view) {
        super.onCreateView(view);
        ImageView imageView = getView().getmIv_ad();

        Glide.with(getView()).load("https://b-ssl.duitang.com/uploads/item/201407/27/20140727021216_tPYdL.jpeg").into(imageView);
        imageView.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).start();

        rxSubscription = Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getView().startMainActivity();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxSubscription.unsubscribe();
    }
}

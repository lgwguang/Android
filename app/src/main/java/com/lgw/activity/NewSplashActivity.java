package com.lgw.activity;

import android.widget.ImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.jaeger.library.StatusBarUtil;
import com.jude.beam.bijection.RequiresPresenter;
import com.lgw.R;
import com.lgw.base.NewBaseActivity;
import com.lgw.presenter.NewSplashPresenter;

@RequiresPresenter(NewSplashPresenter.class)
public class NewSplashActivity extends NewBaseActivity<NewSplashPresenter> {

    ImageView mIv_ad;

    @Override
    public int getContentView() {
        return R.layout.activity_new_splash;
    }

    @Override
    public void initView() {
        StatusBarUtil.setTranslucent(this,39);
        mIv_ad = findViewById(R.id.iv_ad);
    }

    @Override
    public void initListener() {

    }

    public ImageView getmIv_ad() {
        return mIv_ad;
    }

    public void startMainActivity(){
        ActivityUtils.startActivity(NewMainActivity.class);
        overridePendingTransition(R.anim.screen_zoom_out,R.anim.screen_zoom_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIv_ad = null;
    }
}

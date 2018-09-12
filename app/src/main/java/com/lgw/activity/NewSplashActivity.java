package com.lgw.activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.jude.beam.bijection.BeamAppCompatActivity;
import com.jude.beam.bijection.RequiresPresenter;
import com.lgw.R;
import com.lgw.base.NewBaseActivity;
import com.lgw.presenter.NewSplashPresenter;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

@RequiresPresenter(NewSplashPresenter.class)
public class NewSplashActivity extends NewBaseActivity<NewSplashPresenter> {

    ImageView mIv_ad;

    @Override
    public int getContentView() {
        return R.layout.activity_new_splash;
    }

    @Override
    public void initView() {
        mIv_ad = findViewById(R.id.iv_ad);
    }

    @Override
    public void initListener() {
        XGPushConfig.enableDebug(this, true);
        String token = XGPushConfig.getToken(this);
        LogUtils.i("token:"+token);
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
    }
}

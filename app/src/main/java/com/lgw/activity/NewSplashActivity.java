package com.lgw.activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.widget.ImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.jude.beam.bijection.BeamAppCompatActivity;
import com.jude.beam.bijection.RequiresPresenter;
import com.lgw.R;
import com.lgw.presenter.NewSplashPresenter;

@RequiresPresenter(NewSplashPresenter.class)
public class NewSplashActivity extends BeamAppCompatActivity<NewSplashPresenter> {

    ImageView mIv_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);
        mIv_ad = findViewById(R.id.iv_ad);
    }

    public ImageView getmIv_ad() {
        return mIv_ad;
    }

    public void startMainActivity(){
        ActivityUtils.startActivity(NewMainActivity.class);
        overridePendingTransition(R.anim.screen_zoom_out,R.anim.screen_zoom_out);
        finish();
    }
}

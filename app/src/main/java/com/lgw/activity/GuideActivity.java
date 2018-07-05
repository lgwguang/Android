package com.lgw.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.lgw.R;
import com.lgw.base.BaseActivity;
import com.lgw.view.CountDownView;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    ImageView iv_ad;
    CountDownView cdvTime;

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        iv_ad = findViewById(R.id.iv_ad);
        cdvTime = findViewById(R.id.cdv_time);
        Glide.with(this).load("https://b-ssl.duitang.com/uploads/item/201407/27/20140727021216_tPYdL.jpeg").into(iv_ad);
        initCountDownView();
    }

    private void initCountDownView() {
        cdvTime.setTime(2);
        cdvTime.start();
        cdvTime.setOnLoadingFinishListener(() -> {
            LogUtils.d("======进入主页面======");
            Intent intent = new Intent(mContext, NewMainActivity.class);
            startActivity(intent);
            GuideActivity.this.finish();
        });
    }

    @Override
    public void initListener() {
        cdvTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cdvTime.stop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cdvTime != null && cdvTime.isShown()) {
            cdvTime.stop();
        }
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
     /*TimerTask timerTask = new TimerTask() {
            int timeout = 10;
            @Override
            public void run() {
                if (timeout < 1) {
                    mHandler.post(() -> {
                        stopTimer(timer);
                    });
                    return;
                }
                timeout--;
                mHandler.post(() -> {
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);*/

        /*private void stopTimer(Timer timer) {
        if (timer == null) {
            return;
        }
        timer.cancel();
        startActivity(MainActivity.class);
    }*/
}

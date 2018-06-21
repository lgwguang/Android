package com.lgw.activity;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgw.R;
import com.lgw.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class GuideActivity extends BaseActivity {

    TextView tv_countdown;
    ImageView iv_ad;

    Handler mHandler;
    Timer timer;

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        mHandler = new Handler();
        timer = new Timer();
        tv_countdown = findViewById(R.id.tv_countdown);
        iv_ad = findViewById(R.id.iv_ad);

        tv_countdown.setText("10秒");
        TimerTask timerTask = new TimerTask() {
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
                    tv_countdown.setText(timeout + "秒");
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void initListener() {

    }

    private void stopTimer(Timer timer) {
        if (timer == null) {
            return;
        }
        timer.cancel();
        startActivity(MainActivity.class);
    }

}

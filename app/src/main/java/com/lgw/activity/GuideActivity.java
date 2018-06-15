package com.lgw.activity;

import com.lgw.R;
import com.lgw.base.BaseActivity;
import com.lgw.widget.ClearableEditText;

import java.util.Timer;
import java.util.TimerTask;

public class GuideActivity extends BaseActivity {

    ClearableEditText et;

    @Override
    public int getContentView() {
        return R.layout.activity_guide;
    }
    int i = 0;
    @Override
    public void initView() {

        et = findViewById(R.id.et_textview);
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> et.setText("循环次数：" + String.valueOf(i)));
                i++;
                if(i>9){
                    timer.cancel();
                    startActivity(MainActivity.class);
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void initListener() {

    }
}

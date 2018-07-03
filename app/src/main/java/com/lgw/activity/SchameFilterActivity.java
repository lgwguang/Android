package com.lgw.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public class SchameFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_schame_filter);
        Uri uri = getIntent().getData();
//        uri = "https://www.baidu.com/";
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}

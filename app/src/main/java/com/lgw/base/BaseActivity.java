package com.lgw.base;


import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;

public class BaseActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

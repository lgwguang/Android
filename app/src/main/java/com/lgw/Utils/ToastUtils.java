package com.lgw.Utils;

import android.app.Activity;
import android.widget.Toast;

public final class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("不能实例化");
    }

    public static void showToast(Activity activity, String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(() -> {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            });
        }
    }

}

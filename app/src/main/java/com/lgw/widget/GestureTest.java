package com.lgw.widget;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/7/3
 * ================================
 */
public class GestureTest extends GestureDetector {
    public GestureTest(Context context, OnGestureListener listener) {
        super(context, listener);
    }

    public GestureTest(Context context, OnGestureListener listener, Handler handler) {
        super(context, listener, handler);
    }
}

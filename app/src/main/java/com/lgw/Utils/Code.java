package com.lgw.Utils;

import android.os.SystemClock;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/7/19
 * ================================
 */
public class Code {

    public interface CallBack{
        void method(Object object);
    }

    public static void getData(CallBack mCallBack){
        new Thread(() -> SystemClock.sleep(10)).start();
        mCallBack.method("完成");

    }


}

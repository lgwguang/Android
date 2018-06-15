package com.lgw.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/6/13
 * ================================
 */
public class BaseFragment extends Fragment {

    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }
}

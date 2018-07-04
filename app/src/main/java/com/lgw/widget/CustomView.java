package com.lgw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/7/4
 * ================================
 */
public class CustomView extends RelativeLayout {

    View tv_text;
    int widthPixels,heightPixels;
    public CustomView(Context context) {
        this(context,null);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthPixels/2,heightPixels/2);
        //setMeasuredDimension(ScreenUtils.getScreenWidth(),300);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        tv_text = getChildAt(0);
        RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams) tv_text.getLayoutParams();

        tv_text.layout(15,50,widthPixels/2,heightPixels/2);
       /* View child = getChildAt(i);
        if (child.getVisibility() != GONE) {
            RelativeLayout.LayoutParams st =
                    (RelativeLayout.LayoutParams) child.getLayoutParams();
            child.layout(st.mLeft, st.mTop, st.mRight, st.mBottom);*/
//        super.onLayout(changed, l, t, r, b);
    }
}

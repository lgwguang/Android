package com.lgw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/6/25
 * ================================
 */
public class GridViewInConstraint extends GridView {
    public GridViewInConstraint(Context context) {
        super(context);
    }

    public GridViewInConstraint(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int spec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, spec);

    }
}

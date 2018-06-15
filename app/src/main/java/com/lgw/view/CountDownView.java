package com.lgw.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/6/12
 * ================================
 */
public class CountDownView extends View {

    private Context mContext;
    private Paint mPaintBackGround;
    private Paint mPaintArc;
    private Paint mPaintText;
    private int mRetreatType;
    private float mPaintArcWidth;
    private int mCircleRadius;
    private int mPaintArcColor;
    private int mPaintBackGroundColor;
    private int mLoadingTime;
    private String mLoadingTimeUnit;
    private int mTextColor;
    private int mTextSize;
    private int location;
    private float startAngle;
    private float mmSweepAngleStart;
    private float mmSweepAngleEnd;
    private float mSweepAngle;
    private String mText;
    private int mWidth;
    private int mHeight;
    private AnimatorSet set;
    private CountDownView.OnLoadingFinishListener loadingFinishListener;

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    public interface OnLoadingFinishListener {
        void finish();
    }
}

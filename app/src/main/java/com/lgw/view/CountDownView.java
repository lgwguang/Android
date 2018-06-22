package com.lgw.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SizeUtils;
import com.lgw.R;

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
        this(context, (AttributeSet) null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaintArcColor = Color.parseColor("#ee49565C");
        this.mPaintBackGroundColor = Color.parseColor("#FFFFFF");
        this.mLoadingTimeUnit = "";
        this.mTextColor = Color.parseColor("#000000");
        this.mText = "";
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        this.mRetreatType = array.getInt(R.styleable.CountDownView_cd_retreat_type, 1);
        this.location = array.getInt(R.styleable.CountDownView_cd_location, 1);
        this.mCircleRadius = (int) array.getDimension(R.styleable.CountDownView_cd_circle_radius, (float) SizeUtils.dp2px(25.0F));
        this.mPaintArcWidth = array.getDimension(R.styleable.CountDownView_cd_arc_width, (float) SizeUtils.dp2px(3.0F));
        this.mPaintArcColor = array.getColor(R.styleable.CountDownView_cd_arc_color, this.mPaintArcColor);
        this.mTextSize = (int) array.getDimension(R.styleable.CountDownView_cd_text_size, (float) SizeUtils.dp2px(14.0F));
        this.mTextColor = array.getColor(R.styleable.CountDownView_cd_text_color, this.mTextColor);
        this.mPaintBackGroundColor = array.getColor(R.styleable.CountDownView_cd_bg_color, this.mPaintBackGroundColor);
        this.mLoadingTime = array.getInteger(R.styleable.CountDownView_cd_animator_time, 3);
        this.mLoadingTimeUnit = array.getString(R.styleable.CountDownView_cd_animator_time_unit);
        if (TextUtils.isEmpty(this.mLoadingTimeUnit)) {
            this.mLoadingTimeUnit = "";
        }

        array.recycle();
        this.init();
    }

    @TargetApi(16)
    private void init() {
        this.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.round_bg));
        this.mPaintBackGround = new Paint();
        this.mPaintBackGround.setStyle(Paint.Style.FILL);
        this.mPaintBackGround.setAntiAlias(true);
        this.mPaintBackGround.setColor(this.mPaintBackGroundColor);
        this.mPaintArc = new Paint();
        this.mPaintArc.setStyle(Paint.Style.STROKE);
        this.mPaintArc.setAntiAlias(true);
        this.mPaintArc.setColor(this.mPaintArcColor);
        this.mPaintArc.setStrokeWidth(this.mPaintArcWidth);
        this.mPaintText = new Paint();
        this.mPaintText.setStyle(Paint.Style.STROKE);
        this.mPaintText.setAntiAlias(true);
        this.mPaintText.setColor(this.mTextColor);
        this.mPaintText.setTextSize((float) this.mTextSize);
        if (this.mLoadingTime < 0) {
            this.mLoadingTime = 3;
        }

        if (this.location == 1) {
            this.startAngle = -180.0F;
        } else if (this.location == 2) {
            this.startAngle = -90.0F;
        } else if (this.location == 3) {
            this.startAngle = 0.0F;
        } else if (this.location == 4) {
            this.startAngle = 90.0F;
        }

        if (this.mRetreatType == 1) {
            this.mmSweepAngleStart = 0.0F;
            this.mmSweepAngleEnd = 360.0F;
        } else {
            this.mmSweepAngleStart = 360.0F;
            this.mmSweepAngleEnd = 0.0F;
        }

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(this.mCircleRadius * 2, this.mCircleRadius * 2);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle((float) (this.mWidth / 2), (float) (this.mHeight / 2), (float) (this.mWidth / 2) - this.mPaintArcWidth, this.mPaintBackGround);
        RectF rectF = new RectF(0.0F + this.mPaintArcWidth / 2.0F, 0.0F + this.mPaintArcWidth / 2.0F, (float) this.mWidth - this.mPaintArcWidth / 2.0F, (float) this.mHeight - this.mPaintArcWidth / 2.0F);
        canvas.drawArc(rectF, this.startAngle, this.mSweepAngle, false, this.mPaintArc);
        float mTextWidth = this.mPaintText.measureText(this.mText, 0, this.mText.length());
        float dx = (float) (this.mWidth / 2) - mTextWidth / 2.0F;
        Paint.FontMetricsInt fontMetricsInt = this.mPaintText.getFontMetricsInt();
        float dy = (float) ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom);
        float baseLine = (float) (this.mHeight / 2) + dy;
        canvas.drawText(this.mText, dx, baseLine, this.mPaintText);
    }

    public void start() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{this.mmSweepAngleStart, this.mmSweepAngleEnd});
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CountDownView.this.mSweepAngle = (Float) valueAnimator.getAnimatedValue();
                CountDownView.this.invalidate();
            }
        });
        ValueAnimator animator1 = ValueAnimator.ofInt(new int[]{this.mLoadingTime, 0});
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int time = (Integer) valueAnimator.getAnimatedValue();
                CountDownView.this.mText = time + CountDownView.this.mLoadingTimeUnit;
            }
        });
        this.set = new AnimatorSet();
        this.set.playTogether(new Animator[]{animator, animator1});
        this.set.setDuration((long) (this.mLoadingTime * 1000));
        this.set.setInterpolator(new LinearInterpolator());
        this.set.start();
        this.set.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                CountDownView.this.clearAnimation();
                if (CountDownView.this.loadingFinishListener != null) {
                    CountDownView.this.loadingFinishListener.finish();
                }

            }
        });
    }

    public void stop() {
        if (this.set != null && this.set.isRunning()) {
            this.set.cancel();
        }

    }

    public void setTime(int time) {
        this.mLoadingTime = time;
    }

    public void setOnLoadingFinishListener(CountDownView.OnLoadingFinishListener listener) {
        this.loadingFinishListener = listener;
    }

    public interface OnLoadingFinishListener {
        void finish();
    }
}

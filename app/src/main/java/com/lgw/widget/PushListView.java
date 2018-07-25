package com.lgw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.lgw.R;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/7/17
 * ================================
 */
public class PushListView extends ListView {

    private int mImageViewHeight = 0;
    private boolean refresh = false;
    private ImageView mImageView,image_icon;

    public PushListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.header_height);
    }
    public void setZoomImageView(ImageView iv,ImageView icon) {
        mImageView = iv;
        image_icon = icon;
    }

    public void isRefresh( boolean a){
        if(a == true){
            image_icon.clearAnimation();
            image_icon.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        /*
        dx,dy增量
        deltaY:
        -:下拉过度
        +:上拉过度
         */
        if (deltaY < 0) {
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            image_icon.setRotation(image_icon.getRotation() - deltaY);
            mImageView.requestLayout();
        } else {
            //缩小
            if (mImageView.getHeight()>mImageViewHeight) {
                mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
                mImageView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //让头部在上滑时缩小，监听
        View header = (View) mImageView.getParent();
        //header.getTop()<0头部滑出的距离
        if (header.getTop() < 0 && mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = mImageView.getHeight() + header.getTop();
            header.layout(header.getLeft(), 0, header.getRight(), header.getHeight());
            mImageView.requestLayout();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_UP){//松开手弹回初始位置
            ResetAnimation animation = new ResetAnimation(mImageView,mImageViewHeight);
            animation.setDuration(300);
            mImageView.startAnimation(animation);
            image_icon.startAnimation(rotationAnimation());
        }
        return super.onTouchEvent(ev);
    }

    public class ResetAnimation extends Animation{
        private ImageView mTv;
        private int targetHeight;
        private int originalHeight;
        private int extraHeight;
        public ResetAnimation(ImageView iv, int targetHeight) {
            this.mTv = iv;
            this.targetHeight = targetHeight;//最终恢复的高度
            this.originalHeight = mTv.getHeight();//现在的高度
            this.extraHeight = originalHeight - targetHeight;//高度差
        }
        //下拉过度，弹回去动画
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            //interpolatedTime(0.0 to 1.0)   执行的百分比
            mImageView.getLayoutParams().height = (int) (originalHeight - extraHeight * interpolatedTime);
            mImageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
    //刷新icon动画集（旋转刷新）
    public Animation rotationAnimation(){
        Animation  mRotateAnimation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        return  mRotateAnimation;
    }
}

package com.lgw.activity;

import android.animation.ValueAnimator;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.lgw.R;

public class TextActivity extends AppCompatActivity {

    SpanUtils mSpanUtils;
    Shader mShader;

    SpannableStringBuilder animSsb;
    ValueAnimator valueAnimator;

    float         mShaderWidth;
    float    density;

    Matrix matrix;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        tv = findViewById(R.id.textView);

        density = getResources().getDisplayMetrics().density;
        matrix = new Matrix();

        mShaderWidth = 64 * density * 4;
        mShader = new LinearGradient(0, 0, mShaderWidth, 0,
                getResources().getIntArray(R.array.rainbow), null, Shader.TileMode.REPEAT);


        mSpanUtils = new SpanUtils().appendLine("彩虹动画").setFontSize(64, true).setShader(mShader);

        animSsb = mSpanUtils.create();
        startAnim();
    }

    private void startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // shader
                matrix.reset();
                matrix.setTranslate((Float) animation.getAnimatedValue() * mShaderWidth, 0);
                mShader.setLocalMatrix(matrix);
                // update
                tv.setText(animSsb);
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(600 * 3);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }
}

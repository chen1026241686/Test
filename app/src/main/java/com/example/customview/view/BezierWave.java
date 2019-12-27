package com.example.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 贝塞尔曲线波浪
 */
public class BezierWave extends View {


    private Paint mPaint;
    private Path mPath;
    /**
     * 一个周期长度
     */
    private int mItemWaveLength;
    private int mItemtVaveHeight;

    int originY = 300;
    int halfWaveLen;

    private int width, height, dx;

    private ValueAnimator animator;

    {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        mItemWaveLength = width / 3;
        mItemtVaveHeight = mItemWaveLength / 4;
        halfWaveLen = mItemWaveLength / 2;
        initAnim();
    }

    public BezierWave(Context context) {
        super(context);
    }

    public BezierWave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public BezierWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAnim() {
        animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                Log.e("FFF", "dx=" + dx);
                postInvalidate();
            }
        });
    }

    public void startAnim() {
        if (animator != null) {
            animator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animator == null) {
            initAnim();
        }
        mPath.reset();
        mPath.moveTo(-mItemWaveLength + dx, originY);
        for (int i = -mItemWaveLength; i <= width + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -mItemtVaveHeight, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, mItemtVaveHeight, halfWaveLen, 0);
        }
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

}

package com.example.customview.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.customview.R;

/**
 * 会发光，用模糊来实现
 */
public class FaGuang extends View {

    private Paint paintCircle;

    private int width;
    private int height;

    {
        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
    }

    private ValueAnimator valueAnimatorBlur;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        paintCircle.setColor(Color.RED);
        valueAnimatorBlur = ValueAnimator.ofFloat(50, 80, 50);
        valueAnimatorBlur.setDuration(2000);
        valueAnimatorBlur.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorBlur.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimatorBlur.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimatorBlur.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                paintCircle.setMaskFilter(null);
                float value = (float) animation.getAnimatedValue();
                //这种实现不好，只绘制了外部的模糊效果，并没有绘制内部的内容。试试Shadow
                paintCircle.setMaskFilter(new BlurMaskFilter(value, BlurMaskFilter.Blur.OUTER));
                invalidate();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(valueAnimatorBlur);
        set.start();
    }

    public FaGuang(Context context) {
        super(context);
    }

    public FaGuang(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public FaGuang(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, 100, paintCircle);
    }
}

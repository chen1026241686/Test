package com.example.customview.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 会发光,用shadow实现，这种效果比用blur好
 */
public class FaGuang2 extends View {

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

        paintCircle.setColor(Color.BLACK);

        valueAnimatorBlur = ValueAnimator.ofFloat(30, 50, 30);
        valueAnimatorBlur.setDuration(2000);
        valueAnimatorBlur.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorBlur.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimatorBlur.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimatorBlur.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                paintCircle.clearShadowLayer();
                float value = (float) animation.getAnimatedValue();
                paintCircle.setShadowLayer(value, 0, 0, Color.RED);
                invalidate();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(valueAnimatorBlur);
        set.start();
    }

    public FaGuang2(Context context) {
        super(context);
    }

    public FaGuang2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public FaGuang2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, 100, paintCircle);
    }
}

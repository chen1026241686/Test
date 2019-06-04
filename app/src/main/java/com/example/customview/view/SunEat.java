package com.example.customview.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 日食
 */
public class SunEat extends View {


    private Paint paintCircle;

    private int width;
    private int height;


    private Context mContext;

    {

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

    }

    public SunEat(Context context) {
        super(context);
        mContext = context;
    }

    public SunEat(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    public SunEat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintCircle.setColor(Color.BLACK);
        paintCircle.setMaskFilter(null);
        canvas.drawRect(0, 0, width, height, paintCircle);

        paintCircle.setColor(Color.RED);
        paintCircle.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        canvas.drawCircle(width / 2, height / 2, 100, paintCircle);
    }
}

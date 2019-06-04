package com.example.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * portfudd
 */
public class RedPoint extends FrameLayout {


    private Paint mPaint;

    private Path path;

    private float startX = 100;
    private float startY = 100;

    private int radius = 30;

    {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
        path.addCircle(startX, startY, radius, Path.Direction.CW);
    }

    public RedPoint(@NonNull Context context) {
        super(context);
    }

    public RedPoint(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RedPoint(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RedPoint(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                path.reset();
                path.addCircle(startX, startY, radius, Path.Direction.CW);

                float endX = event.getX();
                float endY = event.getY();

                addBezier(endX, endY);

                path.addCircle(endX, endY, radius, Path.Direction.CW);
                invalidate();
                break;
            default:
                break;
        }

        return true;
    }


    private void addBezier(float endX, float endY) {
        path.moveTo(startX, startY);


        float centerX = (startX + endX) / 2;
        float centerY = (startY + endY) / 2;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, mPaint);
    }
}

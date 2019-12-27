package com.example.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 贝塞尔曲线画板
 * <p>
 * 手写
 */
public class BezierDrawBoard extends View {


    private Paint paint;
    private Path path;


    private float preX, preY;

    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }


    public BezierDrawBoard(Context context) {
        super(context);
    }

    public BezierDrawBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public BezierDrawBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                preX = event.getX();
                preY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                //这里的控制点是上一次画的位置，终点是move的坐标和上一次终点的中间位置
                path.quadTo(preX, preY, (event.getX() + preX) / 2, (event.getY() + preY) / 2);
                preX = event.getX();
                preY = event.getY();
                invalidate();
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}

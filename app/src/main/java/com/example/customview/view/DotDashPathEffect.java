package com.example.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 一个虚线效果
 * <p>
 * TODO
 * 1.可以设置方向
 */
public class DotDashPathEffect extends View {


    private Paint paint;

    private DashPathEffect dashPathEffect;

    private Path path;

    private int width;
    private int height;

    /**
     * 顶点和底点距离整个View的padding值
     */
    private int padding = 10;

    /**
     * 顶点和底点半径
     */
    private int radius = 10;

    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        dashPathEffect = new DashPathEffect(new float[]{10.0f, 5.0f}, 0);

        path = new Path();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        path.reset();
        path.moveTo(width / 2, padding + radius);
        path.lineTo(width / 2, height - padding - radius);

    }

    public DotDashPathEffect(Context context) {
        super(context);
    }

    public DotDashPathEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DotDashPathEffect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setPathEffect(null);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawCircle(width / 2, padding + radius / 2, radius, paint);
        canvas.drawCircle(width / 2, height - padding - radius / 2, radius, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(path, paint);

    }
}

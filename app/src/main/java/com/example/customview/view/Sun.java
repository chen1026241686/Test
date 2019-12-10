package com.example.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview.R;

/**
 * 太阳
 */
public class Sun extends View {


    private Bitmap bitmap;
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

    public Sun(Context context) {
        super(context);
        mContext = context;
    }

    public Sun(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.dog4);
    }


    public Sun(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        paintCircle.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(width / 2, height / 2, 100, paintCircle);

//        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),new Rect(0,0,300,300),paintCircle);
//        canvas.drawBitmap(bitmap,new Rect(0,0,100,100),new Rect(0,0,300,300),paintCircle);
    }
}

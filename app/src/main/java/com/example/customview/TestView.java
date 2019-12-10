package com.example.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class TestView extends View {
    Paint paint = new Paint();

    {

        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setShader(new LinearGradient(100,100,200,600,Color.RED,Color.GREEN, Shader.TileMode.CLAMP));
    }

    public TestView(Context context) {
        super(context);
    }

    Bitmap bitmap;

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);


        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat);


    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth()/2,getHeight()/2,100,paint);

    }
}

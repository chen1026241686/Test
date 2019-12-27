package com.example.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class TouchView extends androidx.appcompat.widget.AppCompatTextView {
    private int width;
    private int height;
    private Paint paint = new Paint();
    private int radius;

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("FFF", "TextView-->dispatchTouchEvent-->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("FFF", "TextView-->dispatchTouchEvent-->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("FFF", "TextView-->dispatchTouchEvent-->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("FFF", "TextView-->dispatchTouchEvent-->ACTION_UP");
                break;
            default:
                Log.e("FFF", "TextView-->dispatchTouchEvent-->default");
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        Log.e("FFF", "光标？？？event========" + event.getAction());
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("FFF", "TextView-->onTouchEvent-->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("FFF", "TextView-->onTouchEvent-->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("FFF", "TextView-->onTouchEvent-->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("FFF", "TextView-->onTouchEvent-->ACTION_UP");
                break;
            default:
                Log.e("FFF", "TextView-->onTouchEvent-->default");
                break;
        }
        return super.onTouchEvent(event);
//        return true;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("FFF", "onLayout=" + "[" + left + "," + top + "," + right + "," + bottom + "]ddddddd");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        radius = width < height ? width / 2 : height / 2;

//        setMeasuredDimension(width * 2, height);

        Log.e("FFF", "Measuredwidth=" + getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("FFF", "Drawwidth=" + getWidth());
//        canvas.drawRect(0, 0, width, height, paint);
    }
}

package com.example.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

public class MyFrameLayout extends ViewGroup {
    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        printMeasureSpe(widthMeasureSpec);
    }


    private void printMeasureSpe(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e("FFF", "UNSPECIFIED,size=" + size);
                break;
            case MeasureSpec.EXACTLY:
                Log.e("FFF", "EXACTLY,size=" + size);
                break;
            case MeasureSpec.AT_MOST:
                Log.e("FFF", "AT_MOST,size=" + size);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}

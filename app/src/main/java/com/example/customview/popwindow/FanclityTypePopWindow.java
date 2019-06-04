package com.example.customview.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customview.R;

//import android.widget.PopupWindow;


/**
 * Date: 2019/5/28
 */
public class FanclityTypePopWindow extends PopupWindow {
    private Context mContext;
    private View mContentView;

    public FanclityTypePopWindow(Context context) {
        super(context);
        mContext = context;
        initPop();
    }

    private void initPop() {
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.popwindow, null);

//        Log.e("FFF", "AAAwidth=" + mContentView.getWidth());
//        Log.e("FFF", "AAAwidth=" + mContentView.getHeight());
        mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

//        Log.e("FFF", "BBBmContentView.getLayoutParams().height=" + mContentView.getLayoutParams().height);
//        Log.e("FFF", "BBBmContentView.getLayoutParams().width=" + mContentView.getLayoutParams().width);
//        Log.e("FFF", "BBBwidth=" + mContentView.getWidth() + ",measureW=" + mContentView.getMeasuredWidth());
//        Log.e("FFF", "BBBwidth=" + mContentView.getHeight() + ",measureH=" + mContentView.getMeasuredHeight());

        setContentView(mContentView);
//        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
    }

}

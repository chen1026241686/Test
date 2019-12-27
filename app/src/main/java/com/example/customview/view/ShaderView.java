package com.example.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.customview.R;

public class ShaderView extends View {


    private Paint paint;


    private int width, height;
    private int bmpWidth, bmpHeight;

    private Bitmap rect;

    private Context mContext;

    private String text = "123456";

    private GestureDetector detector;


    /**
     * 1.onDown必须返回true，否则所有的事件都不会触发，因为事件的识别第一个事件就是onDown，如果返回false就代表你不想
     * 该View被识别，所以必须返回true,代表该view可以进行事件识别。
     * 2.快速按下并抬起来会执行onDown,onSingleTapUp
     * 3.按下超过一段事件再抬起来会执行onDown,onShowPress,onLongPress,注意不会执行onSingleTapUp（TODO 为什么不会执行）
     * 4.onLongPress触发之后不会再执行其他方法，例如onScroll,onFling都不会再被执行
     */
    class MyListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("FFF", "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("FFF", "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("FFF", "onSingleTapUp");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("FFF", "onScroll======" + distanceX + "," + distanceY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("FFF", "onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("FFF", "onFling");
            return true;
        }
    }

    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(45);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }


    float x, y;


    public ShaderView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
        init();

    }


    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void init() {
        rect = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dog4);
        bmpWidth = rect.getWidth();
        bmpHeight = rect.getHeight();


        detector = new GestureDetector(mContext, new MyListener());

//        Log.e("FFF", "bmpWidth=" + bmpWidth);
//        Log.e("FFF", "bmpHeight=" + bmpHeight);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }


    private float oneX, oneY, twoX, twoY;
    private float moveOneX, moveOneY, moveTwoX, moveTwoY;

    double scale = 1;


    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int size = event.getPointerCount();
                if (size == 2) {
                    for (int i = 0; i < size; i++) {
                        int pointerID = event.getPointerId(i);
                        int index = event.findPointerIndex(pointerID);
                        if (i == 0) {
                            oneX = event.getX(index);
                            oneY = event.getY(index);
                        } else {
                            twoX = event.getX(index);
                            twoY = event.getY(index);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_SCROLL:
                Log.e("FFF", "ACTION_SCROLL");
                break;
            case MotionEvent.ACTION_MOVE:

                int pointerCount = event.getPointerCount();
                if (pointerCount == 2) {
                    double distanceOne = 0, distanceTwo = 0;
                    for (int i = 0; i < pointerCount; i++) {
                        int pointerID = event.getPointerId(i);
                        int index = event.findPointerIndex(pointerID);

                        if (i == 0) {
                            moveOneX = event.getX(index);
                            moveOneY = event.getY(index);
                            distanceOne = Math.sqrt(Math.pow((moveOneX - oneX), 2) + Math.pow((moveOneY - oneY), 2));
                        } else {
                            moveTwoX = event.getX(index);
                            moveTwoY = event.getY(index);
                            distanceTwo = Math.sqrt(Math.pow((moveTwoX - twoX), 2) + Math.pow((moveTwoY - twoY), 2));
                        }

                        //第一个手指往左滑
                        if (moveOneX - oneX <= 0) {
                            //第二个手指往右边滑
                            if (moveTwoX - oneX > 0) {
                                //放大

                            } else {

                            }
                        }
                        //第一个手指往右滑
                        else if (moveOneX - oneX > 0) {
                            //第二个手指往左
                            if (moveTwoX - oneX < 0) {
                                //缩小
                                distanceOne = -distanceOne;
                                distanceTwo = -distanceTwo;
                            }
                        }

                    }
                    scale = (distanceOne + distanceTwo + getWidth()) / getWidth();
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.e("FFF", "ACTION_UP");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int leftPointer = event.getPointerCount();
                if (leftPointer >= 1) {
                    for (int i = 0; i < leftPointer; i++) {
                        int pointerID = event.getPointerId(i);
                        int index = event.findPointerIndex(pointerID);
                    }
                }
                Log.e("FFF", "ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("FFF", "ACTION_CANCEL");
                break;
        }
        return true;

//        boolean result = detector.onTouchEvent(event);
//        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.scale((float) scale, (float) scale);
        canvas.drawBitmap(rect, 0, 0, paint);

    }
}

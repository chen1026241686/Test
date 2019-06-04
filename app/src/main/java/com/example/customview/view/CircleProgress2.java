package com.example.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.BuildConfig;
import com.example.customview.R;


/**
 * 一个可以有动画效果的圆形进度条
 * <p>
 * 用法：findViewById，然后直接setProgress。
 * <p>
 * TODO
 * 1.圆框的大小可以根据自身的高度宽度调节
 * 2.文字大小可以根据自身的高度宽度调节
 */
public class CircleProgress2 extends View {


    /**
     * 矩形文字的画笔
     */
    private Paint rectangleTextPaint;
    /**
     * tip文字的画笔
     */
    private Paint tipTextPaint;
    /**
     * 中间的文字画笔
     */
    private Paint centerTextPaint;


    private Context mContext;


    /**
     * 文字大小
     */
    private int rectangleTextSize = 40;
    /**
     * 文字大小
     */
    private int centerTextSize = 55;


    private Paint rectanglePaint;
    /**
     * 进度画笔
     */
    private Paint progressPaint;
    /**
     * 弧宽度
     */
    private int circleWidth = 25;

    /**
     * 控件的宽度
     */
    private int width;
    /**
     * 控件的高度
     */
    private int height;
    /**
     * 弧所在的区域高度
     */
    private int circleHeight;

    /**
     * 弧的起始颜色值 FIXME 颜色值不加FF透明度会显示不出来
     */
    private int startColor = 0xffd88f9c;
    /**
     * 弧形的结束颜色值  FIXME 颜色值不加FF透明度会显示不出来
     */
    private int endColor = 0xffd16573;

    /**
     * 矩形中的文字
     */
    private String rectangleText;
    /**
     * 弧形下方文字
     */
    private String tipText;
    /**
     * 弧形中间文字
     */
    private String centerText;


    /**
     * 起始角度
     */
    private final int startAngle = 135;
    /**
     * 扫过的角度
     */
    private final int sweepAngle = 270;

    /**
     * 底部圆角矩形高度
     */
    private final int bottomAreaHeight = 90;

    private final int rectangleHeight = 70;

    /**
     * 弧形半径
     */
    private int radius;
    //=================弧形上下左右==============
    private int circleLeft;
    private int circleTop;
    private int circleRight;
    private int circleBottom;

    //=================矩形上下左右==============
    private int rectangleLeft;
    private int rectangleTop;
    private int rectangleRight;
    private int rectangleBottom;
    private SweepGradient sweepGradient;


    private Rect rectangleRect = new Rect();
    private Rect tipRect = new Rect();
    private Rect centerRect = new Rect();

    private void init(AttributeSet attrs) {


        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
            if (typedArray != null) {
                startColor = typedArray.getColor(R.styleable.CircleProgress_startColor, startColor);
                endColor = typedArray.getColor(R.styleable.CircleProgress_endColor, endColor);
                rectangleText = typedArray.getString(R.styleable.CircleProgress_rectangleText);
                tipText = typedArray.getString(R.styleable.CircleProgress_tipText);
                centerText = typedArray.getString(R.styleable.CircleProgress_centerText);
                if (rectangleText == null) {
                    rectangleText = "";
                }
                if (tipText == null) {
                    tipText = "";
                }
                if (centerText == null) {
                    centerText = "";
                }
                typedArray.recycle();
            }
        }

        rectangleTextPaint = new Paint();
        rectangleTextPaint.setTextSize(rectangleTextSize);
        rectangleTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        rectangleTextPaint.setAntiAlias(true);
        rectangleTextPaint.setColor(0xffffffff);

        tipTextPaint = new Paint();
        tipTextPaint.setTextSize(rectangleTextSize);
        tipTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tipTextPaint.setAntiAlias(true);
        tipTextPaint.setColor(0xff8e9aae);

        centerTextPaint = new Paint();
        centerTextPaint.setTextSize(centerTextSize);
        centerTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        centerTextPaint.setAntiAlias(true);
        centerTextPaint.setColor(0xff000000);

        rectanglePaint = new Paint();
        rectanglePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setColor(endColor);

        progressPaint = new Paint();
        progressPaint.setStrokeWidth(circleWidth);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        calculateText();
    }

    private GestureDetector gestureDetector;


    public CircleProgress2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        gestureDetector = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {
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
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("FFF", "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("FFF", "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("FFF", "onFling");
                return false;
            }
        });
        init(attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;


        if (height < 180 && BuildConfig.DEBUG) {
            throw new RuntimeException("view height is too small");
        }


        circleHeight = height - bottomAreaHeight;
        if (width > circleHeight) {
            radius = circleHeight / 2;
            //计算左上右下边界
            circleLeft = width / 2 - radius + circleWidth;
            circleTop = 0 + circleWidth;
            circleRight = width / 2 + radius - circleWidth;
            circleBottom = circleHeight - circleWidth;
        } else {
            radius = width / 2;

            //计算左上右下边界
            circleLeft = 0 + circleWidth;
            circleTop = circleHeight / 2 - radius + circleWidth;
            circleRight = width - circleWidth;
            circleBottom = circleHeight / 2 + radius - circleWidth;
        }


        rectangleLeft = circleLeft + (int) (radius * (1 - Math.sin(Math.toRadians(startAngle))));
        rectangleTop = circleBottom;
        rectangleRight = circleRight - (int) (radius * (1 - Math.sin(Math.toRadians(startAngle))));
        rectangleBottom = circleBottom + rectangleHeight;

        //shader重置
        progressPaint.setShader(null);
        Matrix matrix = new Matrix();
        matrix.setRotate(startAngle, width / 2, bottomAreaHeight / 2);
        sweepGradient = new SweepGradient(width / 2, bottomAreaHeight / 2, new int[]{startColor, endColor, startColor}, new float[]{0.0f, sweepAngle / 360f, 1.0f});
        //sweepGradient需要先旋转startAngle,否则颜色值开始显示是从0开始
        sweepGradient.setLocalMatrix(matrix);
        progressPaint.setShader(sweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(circleLeft, circleTop, circleRight, circleBottom, startAngle, sweepAngle, false, progressPaint);
        canvas.drawRoundRect(rectangleLeft, rectangleTop, rectangleRight, rectangleBottom, rectangleHeight / 2, rectangleHeight / 2, rectanglePaint);

        //矩形文字
        canvas.drawText(rectangleText, width / 2 - (rectangleRect.right - rectangleRect.left) / 2, rectangleBottom - (rectangleRect.bottom - rectangleRect.top) / 2, rectangleTextPaint);

        //tip文字，圆圈的底部减去文字的高度一半，再减去弧形未闭合的高度
        canvas.drawText(tipText, width / 2 - (tipRect.right - tipRect.left) / 2, circleBottom - (tipRect.bottom - tipRect.top) - (int) (radius * (1 + Math.tan(Math.toRadians(startAngle)))), tipTextPaint);

        canvas.drawText(centerText, width / 2 - (centerRect.right - centerRect.left) / 2, circleBottom - radius + (centerRect.bottom - centerRect.top) / 2, centerTextPaint);
    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("FFF", "ACTION_DOWN");
//                return true;
//            case MotionEvent.ACTION_UP:
//                Log.e("FFF", "ACTION_UP");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("FFF", "ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("FFF", "ACTION_CANCEL");
//                break;
//            default:
//                break;
//        }


        return gestureDetector.onTouchEvent(event);

//        return super.onTouchEvent(event);
    }

    public void setRectangleText(String rectangleText) {
        if (this.rectangleText.equals(rectangleText)) {
            return;
        }
        if (rectangleText == null) {
            rectangleText = "";
        }
        this.rectangleText = rectangleText;
        rectangleTextPaint.getTextBounds(rectangleText, 0, rectangleText.length(), rectangleRect);
        invalidate();
    }

    public void setTipText(String tipText) {
        if (this.tipText.equals(tipText)) {
            return;
        }
        if (tipText == null) {
            tipText = "";
        }
        this.tipText = tipText;
        tipTextPaint.getTextBounds(tipText, 0, tipText.length(), tipRect);
        invalidate();
    }

    public void setCenterText(String centerText) {
        if (this.centerText.equals(centerText)) {
            return;
        }
        if (centerText == null) {
            centerText = "";
        }
        this.centerText = centerText;
        centerTextPaint.getTextBounds(centerText, 0, centerText.length(), centerRect);
        invalidate();
    }

    private void calculateText() {
        rectangleTextPaint.getTextBounds(rectangleText, 0, rectangleText.length(), rectangleRect);
        tipTextPaint.getTextBounds(tipText, 0, tipText.length(), tipRect);
        centerTextPaint.getTextBounds(centerText, 0, centerText.length(), centerRect);
    }
}

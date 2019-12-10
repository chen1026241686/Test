package com.example.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @author ChenYasheng
 * @date 2019/9/16
 * @Description 带有圆圈背景的TextView
 */
public class CircleText extends View {


    /**
     * 默认圆框背景
     */
    private final int DEFAULT_CIRCLE_COLOR = 0xffff0000;
    /**
     * 默认文字颜色
     */
    private final int DEFAULT_TEXT_COLOR = 0xff000000;

    private final int DEFAUTL_TEXT_SIZE = 26;


    /**
     * View的高度，长度，圆圈半径
     */
    private int height, width, radius, textSize;

    private int circleColor = DEFAULT_CIRCLE_COLOR, textColor = DEFAULT_TEXT_COLOR;

    /**
     * 文字内容
     */
    private String textContent;

    /**
     * 圆圈和文字画笔
     */
    private Paint circlePaint, textPaint;

    private Rect textRect, circleRect;

    private Context mContext;

    private OnClickListener l;

    private boolean isPressed = false;

    public CircleText(Context context) {
        this(context, null);
    }

    public CircleText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleText);
            circleColor = a.getColor(R.styleable.CircleText_circleColor, DEFAULT_CIRCLE_COLOR);
            textColor = a.getColor(R.styleable.CircleText_textColor, DEFAULT_TEXT_COLOR);
            textContent = a.getString(R.styleable.CircleText_textContent);
            textSize = a.getDimensionPixelSize(R.styleable.CircleText_textSize, DEFAUTL_TEXT_SIZE);
            a.recycle();
        }
        init();
    }


    private void init() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(AutoUtils.getPercentHeightSizeBigger(textSize));
        textPaint.setTextAlign(Paint.Align.CENTER);


        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.STROKE);

        textRect = new Rect();
        circleRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (circleRect.contains(x, y)) {
                    isPressed = true;
                    return true;
                }
                return false;
            case MotionEvent.ACTION_UP:

                if (isPressed) {
                    if (l != null) {
                        l.onClick(this);
                    }
                    isPressed = false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(textContent)) {
            return;
        }
        textPaint.getTextBounds(textContent, 0, textContent.length(), textRect);
        int textHeight = textRect.bottom - textRect.top;
        //半径是最小的那个。
        radius = Math.min(height, width) / 2;
        //（加上padding可能会超了边界，这里没有做处理）
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint);

        circleRect.left = width / 2 - (radius);
        circleRect.top = height / 2 - (radius);
        circleRect.right = width / 2 + (radius);
        circleRect.bottom = height / 2 + (radius);
        canvas.drawText(textContent, width / 2, height / 2 + textHeight / 2, textPaint);
    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }
}

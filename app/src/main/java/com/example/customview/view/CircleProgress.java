package com.example.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 一个可以有动画效果的圆形进度条
 * <p>
 * 用法：findViewById，然后直接setProgress。
 * <p>
 * TODO
 * 1.圆框的大小可以根据自身的高度宽度调节
 * 2.文字大小可以根据自身的高度宽度调节
 */
public class CircleProgress extends View {


    /**
     *
     */
    private Paint textPaint;

    private String percent;

    /**
     * percent rect
     */
    private Rect rect = new Rect();

    /**
     * 文字大小
     */
    private int textSize = 40;


    private Paint circlePaint;
    /**
     * 进度画笔
     */
    private Paint progressPaint;
    /**
     * 圆圈宽度
     */
    private int circleWidth = 20;

    /**
     * circle半径
     */
    private int radius = 0;

    /**
     * 圈的padding
     */
    private int padding = 20;

    /**
     * 进度
     */
    private float progress = 120;

    /**
     * 矩形左边
     */
    private int rectangleLeft = 0;
    /**
     * 矩形左上
     */
    private int rectangleLeftTop = 0;

    /**
     * 进度动画
     */
    private ValueAnimator valueAnimator;

    /**
     * 控件的宽度
     */
    private int width;
    /**
     * 控件的高度
     */
    private int height;

    {
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setColor(0xff000000);

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(circleWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(0xffc3d5ed);

        progressPaint = new Paint();
        progressPaint.setStrokeWidth(circleWidth);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(0xff00a2dc);

        calculateText();
    }


    public CircleProgress(Context context) {
        super(context);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * set progress,range[0,360]
     *
     * @param progress the progress you want to show
     * @param animate  if show animate
     */
    public void setProgress(float progress, boolean animate) {
        if (progress > 360 || progress < 0) {
            throw new RuntimeException("progress can't be smaller than 0 or bigger than 360");
        }

        if (this.progress == progress) {
            return;
        }

        float oldProgress = this.progress;
        this.progress = progress;

        cancleAnimator();
        if (animate) {
            //动画从旧的progress过度到新的progress
            initValueAnimator(oldProgress, progress);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    CircleProgress.this.progress = (float) animation.getAnimatedValue();
                    calculateText();
                    invalidate();
                }
            });
            valueAnimator.start();
        } else {
            invalidate();
        }
    }

    /**
     * 计算percent文字以及它的位置
     */
    private void calculateText() {
        percent = String.format("%.2f", progress * 100 / 360f) + "%";
        textPaint.getTextBounds(percent, 0, percent.length(), rect);
    }


    public float getCurrentProgress() {
        return progress;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        if (width > height) {
            radius = height / 2 - padding;
            //宽度大于高度，则左上就是0+padding
            rectangleLeftTop = 0 + padding;
            rectangleLeft = width / 2 - radius;
        } else {
            radius = width / 2 - padding;
            //宽度小于高度，则左就是0+padding
            rectangleLeft = 0 + padding;
            rectangleLeftTop = height / 2 - radius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint);
        canvas.drawArc(rectangleLeft, rectangleLeftTop, rectangleLeft + radius * 2, rectangleLeftTop + radius * 2, -90, progress, false, progressPaint);
        //x为中间的宽度减去一半的文字宽度，y为中间的高度减去一半的文字高度
        canvas.drawText(percent, width / 2 - (rect.right - rect.left) / 2, height / 2 + (rect.bottom - rect.top) / 2, textPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancleAnimator();
    }

    /**
     * 初始化动画
     *
     * @param oldProgress
     * @param newProgress
     */
    private void initValueAnimator(float oldProgress, float newProgress) {
        valueAnimator = ValueAnimator.ofFloat(oldProgress, newProgress);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(1200);
    }

    /**
     * cancle animator
     */
    private void cancleAnimator() {
        //如果上一次动画还没执行完成，则取消
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }
}

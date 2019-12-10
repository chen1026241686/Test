package com.example.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.customview.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * 带有扩散效果的ImageView.需要开启动画，本view只适用于recordview,没做适配。想做的话，自己去适配一下也可以
 * 注：本view里面的图片设计图给的是139px,所以这里固定写死了139px
 */
public class RippleImageView extends View {


    private Bitmap bitmap;
    private Paint paintBitmap, paintCircle;


    private ValueAnimator valueAnimatorBlur;


    private int bitmapWH = AutoUtils.getPercentHeightSizeBigger(139);

    private int width;
    private int height;
    private int radius;

    /**
     * 图片外部周围发光范围大小
     */
    private int shinePadding = AutoUtils.getPercentHeightSizeBigger(20);


    /**
     * 扩散alpha值。从255-0
     */
    private int mutiple;

    private boolean isDrawCircle = false;


    private Context mContext;

    {
        paintBitmap = new Paint();
        paintBitmap.setAntiAlias(true);
        paintBitmap.setColor(Color.WHITE);

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setColor(Color.parseColor("#ffF7D5D1"));
        paintCircle.setStyle(Paint.Style.FILL);
        mutiple = 0xff / shinePadding;
    }

    public RippleImageView(Context context) {
        super(context);
        mContext = context;
    }

    public RippleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.record_audio);
    }


    public RippleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("FFF", "bitmap.getWidth()----->"+bitmap.getWidth());
        bitmapWH=bitmap.getWidth();
//        bitmapWH=AutoUtils.getPercentHeightSizeBigger(bitmap.getWidth());
        Log.e("FFF", "bitmapWH-------------->"+bitmapWH);
        setMeasuredDimension(bitmapWH + shinePadding + 10, bitmapWH + shinePadding + 10);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = bitmapWH / 2;
    }

    public void setImage(int drawableId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(getResources(), drawableId, opts);
        invalidate();
    }

    public void stopAnimation() {
        isDrawCircle = false;
        if (valueAnimatorBlur != null) {
            valueAnimatorBlur.cancel();
        }
        invalidate();
    }

    public void startAnimation() {
        isDrawCircle = true;
        if (valueAnimatorBlur == null) {
            valueAnimatorBlur = ValueAnimator.ofFloat(0, shinePadding);
            valueAnimatorBlur.setDuration(2500);
            valueAnimatorBlur.setInterpolator(new DecelerateInterpolator());
            valueAnimatorBlur.setRepeatMode(ValueAnimator.RESTART);
            valueAnimatorBlur.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimatorBlur.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    radius = (int) (bitmapWH / 2 + value);
                    //随着value越来越大，alpha应该越来越小，所以这里需要减去
                    paintCircle.setAlpha((int) ((shinePadding - value) * mutiple));
                    invalidate();
                }
            });
        }
        if (!valueAnimatorBlur.isRunning()) {
            valueAnimatorBlur.start();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isDrawCircle) {
            canvas.drawCircle(width / 2, height / 2, radius, paintCircle);
        }
        //这个圆要绘制，不然的话。图片周围透明的部分和扩散的圆在绘制的时候重叠很难看，想看效果可以先注掉这个圆的绘制
        canvas.drawCircle(width / 2, height / 2, bitmapWH / 2, paintBitmap);

        int startX = (width - shinePadding * 2 - bitmapWH) / 2 + shinePadding;
        int startY = (height - shinePadding * 2 - bitmapWH) / 2 + shinePadding;

//        Log.e("FFF", "shinePadding===" + shinePadding);
//        Log.e("FFF", "radius===" + radius);
//        Log.e("FFF", "bitmapWH===" + bitmapWH);

        canvas.drawBitmap(bitmap, null, new Rect(startX, startY, startX + bitmapWH, startY + bitmapWH), paintBitmap);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimatorBlur != null) {
            valueAnimatorBlur.cancel();
            valueAnimatorBlur = null;
        }
    }

}

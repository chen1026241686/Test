package com.example.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview.R;
import com.example.customview.bean.ArcBean;
import com.example.customview.bean.ArcType;

import java.util.ArrayList;
import java.util.List;

public class CircleProgress3 extends View {


    private int width, height, radius, stroke;


    private final int DEFAULT_PERCENT = 25;

    private Paint paint = new Paint();


    private List<ArcBean> arcList = new ArrayList<>();

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(20);
        paint.setStrokeCap(Paint.Cap.ROUND);

    }


    public CircleProgress3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcStyle);

        initArc();
    }

    private void initArc() {

        ArcBean arcBean1 = new ArcBean(ArcType.HOUSE);
        arcBean1.setText("25%");
        arcBean1.setStartAngle(-90);
        arcBean1.setEndAngle(0);
        arcBean1.setStartColor(Color.parseColor("#FFdb63e3"));
        arcBean1.setEndColor(Color.parseColor("#FFa133e9"));
        arcList.add(arcBean1);


        ArcBean arcBean2 = new ArcBean(ArcType.GOODS);
        arcBean2.setText("26%");
        arcBean2.setStartAngle(0);
        arcBean2.setEndAngle(90);
        arcBean2.setStartColor(Color.parseColor("#FFd65494"));
        arcBean2.setEndColor(Color.parseColor("#FFdc5f8a"));
        arcList.add(arcBean2);


        ArcBean arcBean3 = new ArcBean(ArcType.FOOD);
        arcBean3.setText("27%");
        arcBean3.setStartAngle(90);
        arcBean3.setEndAngle(180);
        arcBean3.setStartColor(Color.parseColor("#FFdf8f6f"));
        arcBean3.setEndColor(Color.parseColor("#FFdfcb5e"));
        arcList.add(arcBean3);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > height) {
            stroke = height / 10;
            radius = (height - stroke) / 2;
        } else {
            stroke = width / 10;
            radius = (width - stroke) / 2;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(null);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        canvas.drawCircle(width / 2, height / 2, radius, paint);


        for (int i = arcList.size() - 1; i >= 0; i--) {
            ArcBean arcBean = arcList.get(i);
            Matrix matrix = new Matrix();
            matrix.setRotate(arcBean.getStartAngle(), width / 2, height / 2);
            //从起始角度到结束角度，颜色需要从
            SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2, new int[]{arcBean.getStartColor(), arcBean.getEndColor(), arcBean.getStartColor()}, new float[]{0.0f, (arcBean.getEndAngle() - arcBean.getStartAngle()) / 360f, 1.0f});
            //sweepGradient需要先旋转startAngle,否则颜色值开始显示是从角度0开始
            sweepGradient.setLocalMatrix(matrix);
            paint.setShader(sweepGradient);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(stroke);
            canvas.drawArc(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius, arcBean.getStartAngle(), arcBean.getEndAngle() - arcBean.getStartAngle(), false, paint);
            paint.setShader(null);
            paint.setColor(Color.GREEN);


            int startAngle = (arcBean.getStartAngle() + arcBean.getEndAngle()) / 2;
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(0);
            canvas.drawText(arcBean.getText(), width / 2 + (float) (radius * Math.cos(startAngle+90)), height / 2 + (float) (radius * Math.sin(startAngle+90)), paint);

        }


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancleAnimator();
    }

    private void cancleAnimator() {
    }
}

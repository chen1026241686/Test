package com.example.customview.view;

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
import android.view.View;

import com.example.customview.R;

/**
 * 绘制文字
 * <p>
 * 1.绘制文字会有依次从上往下是top,ascent,leading,descent,bottom五条线
 * ascent: 系统建议的，绘制单个字符时，字符应当的最高高度所在线
 * descent:系统建议的，绘制单个字符时，字符应当的最低高度所在线
 * top: 可绘制的最高高度所在线
 * leading:基线,canvas在drawText的时候传进去的Y坐标就是基线的位置。
 * bottom: 可绘制的最低高度所在线
 * <p>
 * 2.FontMetrics的ascent，descent是距离基线的距离，不是Y坐标。所以有如下计算
 * ascent = ascent线的y坐标 - leading线的y坐标；所以ascent线的y坐标就是leading+ascent。ascent必定为负数，因为ascent线的Y坐标在leading线的上方
 * descent = descent线的y坐标 - leading线的y坐标；同理descent线的y坐标就是leading+descent。descent必定为负数，因为descent线的Y坐标在leading线的下方
 * <p>
 * 3.paint.measureText(String)测量文字所占用的宽度
 * <p>
 * 4.paint.getFontSpacing();获取推荐的行距，这个是用来绘制多行文字的时候，可以在换行的时候给 y 坐标加上这个值来下移文字
 * <p>
 * 5.getTextBounds(String text, int start, int end, Rect bounds)获取文字显示的区域
 * <p>
 * 注意getTextBounds和measureText区别,前面的Rect.right-rect.left是文字的显示的宽度，而measureText是文字占用的宽度
 * 所以measureText获得的宽度肯定会大于rect.right-rect.left
 */
public class DrawText extends View {


    private Paint paintRect;


    private int posX = 30;
    private int posY = 100;

    private String text = "123456";


    {
        paintRect = new Paint();
        paintRect.setAntiAlias(true);
        paintRect.setTextSize(50);
    }


    public DrawText(Context context) {
        super(context);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public DrawText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint.FontMetrics fm = paintRect.getFontMetrics();
        //文字占用的宽度
        float width = paintRect.measureText(text);
        paintRect.setColor(Color.RED);
        //绘制红色矩形，这个矩形区域也是文字占用的空间大小
        canvas.drawRect(posX, fm.top + posY, width + posX, fm.bottom + posY, paintRect);


        Rect rect = new Rect();
        //获得文字显示的矩形区域
        paintRect.getTextBounds(text, 0, text.length(), rect);
        //绘制绿色矩形，这个是文字显示区域，文字一定会在这个区域里面
        paintRect.setColor(Color.GREEN);
        canvas.drawRect(rect.left + posX, rect.top + posY, rect.right + posX, rect.bottom + posY, paintRect);

        //绘制文字
        paintRect.setColor(Color.WHITE);
        canvas.drawText(text, posX, posY, paintRect);


    }
}

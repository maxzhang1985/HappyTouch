package com.kuailedian.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.kuailedian.happytouch.R;

/**
 * Created by 磊 on 2015/5/25.
 */
public class MyEditText extends EditText {
    // 画笔 用来画下划线
    private Paint paint;

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray =  context.obtainStyledAttributes(attrs, R.styleable.MyEditTextAttrs);

        int lineColor = typedArray.getColor(R.styleable.MyEditTextAttrs_LineColor,Color.GRAY);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeWidth(3.0f);

        // 开启抗锯齿 较耗内存
        paint.setAntiAlias(true);

        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int lineY = getHeight();
        canvas.drawLine(0, lineY, this.getWidth(), lineY, paint);


    }


    public void setLineColor(int color )
    {
        paint.setColor(color);
    }


}

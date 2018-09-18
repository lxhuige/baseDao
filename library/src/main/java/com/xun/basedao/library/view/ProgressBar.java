package com.xun.basedao.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ProgressBar extends View {


    private Paint mPaint;
    //圆点坐标
    private float centerX = 100;
    private float centerY = 100;
    //半径
    private float radius = 90;

    private int max = 100;
    private int progress = 0;

    //内外圆差
    private int progressWidth = 10;


    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        canvas.drawCircle(centerX, centerY, radius - progressWidth, mPaint);

        if (progress == 0) return;

        float v = (float) progress / max;
        String text = (int) (v * 100) + "%";
        mPaint.setTextSize(30);
        mPaint.setStrokeWidth(0);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        float measureText = mPaint.measureText(text) / 2;
        float baselineY = centerY - (mPaint.ascent() + mPaint.descent()) / 2;
        canvas.drawText(text, centerX - measureText, baselineY, mPaint);


        RectF rectF = new RectF(centerX - radius+ progressWidth/2, centerY - radius+ progressWidth/2, centerX + radius- progressWidth/2, centerY + radius- progressWidth/2);
        mPaint.setStrokeWidth(progressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, -90f, 360f * v, false, mPaint);

    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }
}

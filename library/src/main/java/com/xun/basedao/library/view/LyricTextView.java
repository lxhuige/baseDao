package com.xun.basedao.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class LyricTextView extends AppCompatTextView {
    private LinearGradient linearGradient;
    private Matrix matrix;
    private float translateX;
    private float deltaX = 20;
    public LyricTextView(Context context) {
        this(context, null);
    }

    public LyricTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       Paint mPaint = getPaint();
        //GradientSize=两个文字的大小
        String text = getText().toString();
        float textWidth = mPaint.measureText(text);
        int GradientSize =(int) (3*textWidth/text.length());
        linearGradient = new LinearGradient(-GradientSize, 0, 0, 0, new int[]{0x22ff0000,0xffff0000,0x22ff0000}, new float[]{0,0.5f,1}, Shader.TileMode.CLAMP);//边缘融合
        mPaint.setShader(linearGradient);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        float textWidth = getPaint().measureText(getText().toString());
        translateX += deltaX;
        if(translateX > textWidth + 1|| translateX < 1){
            deltaX = -deltaX;
        }
//		matrix.setScale(sx, sy)
        matrix.setTranslate(translateX, 0);
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(100);
    }
}

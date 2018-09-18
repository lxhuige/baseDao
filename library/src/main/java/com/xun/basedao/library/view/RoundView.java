package com.xun.basedao.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class RoundView extends AppCompatImageView {

    private  BitmapShader bitmapShader;
//    private  Bitmap bitmap;
    private Paint mPaint;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {

        if (drawable!=null){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap  bitmap = bitmapDrawable.getBitmap();
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
            BitmapShader  bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint = new Paint();
            mPaint.setShader(bitmapShader);
        }
        super.setImageDrawable(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getWidth()/2,getWidth()/2,mPaint);
    }
}

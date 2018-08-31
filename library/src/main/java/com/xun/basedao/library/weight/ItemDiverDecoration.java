package com.xun.basedao.library.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDiverDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    private int left, top, right, bottom;
    private int mHeight;
    //列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private int mOrientation;
    //是否划线
    private boolean isDraw = true;

    /**
     * @param draw false 只占位不划线  ， true 划线
     */
    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public ItemDiverDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDrawable = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(LinearLayoutManager.VERTICAL);
    }

    public ItemDiverDecoration(Context context, int orientation, @DrawableRes int res) {
        mDrawable = ContextCompat.getDrawable(context, res);
        setOrientation(orientation);
    }

    public ItemDiverDecoration(Context context, @DrawableRes int res) {
        this(context, LinearLayoutManager.VERTICAL, res);
    }

    public void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("哥们,逗我ma?非水平和线性的枚举类型");
        }
        this.mOrientation = orientation;
        if (mDrawable == null) return;
        if (orientation == LinearLayoutManager.VERTICAL) {
            mHeight = mDrawable.getIntrinsicHeight();
        } else {
            mHeight = mDrawable.getIntrinsicWidth();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (!isDraw || mDrawable == null) return;
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop() + this.top;
        int bottom = parent.getHeight() - parent.getPaddingBottom() - this.bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin + Math.round(child.getTranslationX());
            int right = left + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    //划水平线
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + this.left;
        final int right = parent.getWidth() - parent.getPaddingRight() - this.right;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin + Math.round(child.getTranslationY());
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    /**
     * recycleView item分割线设置padding，及分割线不贴边
     *
     * @param left  距离左边距
     * @param right 距离右边距
     */
    public void setVerticalPadding(int left, int right) {
        this.left = left;
        this.right = right;
    }

    /**
     * recycleView item分割线设置padding，及分割线不贴边
     */
    public void setHorizontalPadding(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    //1.调用此方法（首先会先获取条目之间的间隙宽度---Rect矩形区域）
    // 获得条目的偏移量(所有的条目都回调用一次该方法)
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){//垂直
            outRect.set(0, 0, 0, mHeight);
        }else{//水平
            outRect.set(0, 0, mHeight, 0 );
        }
    }
}

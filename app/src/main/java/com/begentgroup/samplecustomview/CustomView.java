package com.begentgroup.samplecustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dongja94 on 2016-04-27.
 */
public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        requestLayout();
        invalidate();
    }

    Paint mPaint;
    Bitmap mBitmap;
    int xBitmap, yBitmap;

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft() + getPaddingRight();
        int height = getPaddingTop() + getPaddingBottom();
        if (mBitmap != null) {
            width += mBitmap.getWidth();
            height += mBitmap.getHeight();
        }

//        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        int size = MeasureSpec.getSize(widthMeasureSpec);
//        switch (mode) {
//            case MeasureSpec.AT_MOST:
//                width = (width < size)? width : size;
//                break;
//            case MeasureSpec.EXACTLY:
//                width = size;
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                break;
//        }

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height,heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width, height;
        width = (right - left) - (getPaddingLeft() + getPaddingRight());
        height = (bottom - top) - (getPaddingTop() + getPaddingBottom());

        if (mBitmap != null) {
            width -= mBitmap.getWidth();
            height -= mBitmap.getHeight();
        }

        xBitmap = getPaddingLeft() + width / 2;
        yBitmap = getPaddingTop() + height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (mBitmap != null) {
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
            mPaint.setColorFilter(cf);
            canvas.drawBitmap(mBitmap, xBitmap, yBitmap, mPaint);
        }
    }
}

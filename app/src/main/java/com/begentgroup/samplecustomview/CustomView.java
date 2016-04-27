package com.begentgroup.samplecustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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

    private static final String TAG = "CustomView";
    Paint mPaint;
    Bitmap mBitmap;
    int xBitmap, yBitmap;
    GestureDetector mDetector;
    Matrix mMatrix;

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_1);
        mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i(TAG, "scroll");
                mMatrix.postTranslate(-distanceX, -distanceY);
                invalidate();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(getContext(), "Double Tap" , Toast.LENGTH_SHORT).show();
                mMatrix.postScale(1.5f, 1.5f, e.getX(), e.getY());
                invalidate();
                return true;
            }
        });
        mMatrix = new Matrix();
        mMatrix.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isUse = mDetector.onTouchEvent(event);
        return isUse || super.onTouchEvent(event);
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
        mMatrix.setTranslate(xBitmap, yBitmap);
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
            canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        }
    }
}

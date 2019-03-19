package com.example.testwifi.page;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class ViewFlipperView extends ViewFlipper {
    private GestureDetector gestureDetector;
    private Context mContext;

    public ViewFlipperView(Context context) {
        super(context);
        init(context);
    }

    public ViewFlipperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private int heigth, width;

    private void init(Context context) {
        mContext = context;
        setFlipInterval(1000);
        gestureDetector = new GestureDetector(context, new MySimpleGestureListeners());

        DisplayMetrics dm = getResources().getDisplayMetrics();
        heigth = dm.heightPixels;
        width = dm.widthPixels;
        addData();
    }

    public void addData() {
        LinearLayout ly = new LinearLayout(mContext);
        ly.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, heigth);
        addView(ly, lp);

        ly = new LinearLayout(mContext);
        lp = new LinearLayout.LayoutParams(width, heigth);
        addView(ly, lp);

        ly = new LinearLayout(mContext);
        ly.setBackgroundColor(Color.GREEN);
        lp = new LinearLayout.LayoutParams(width, heigth);
        addView(ly, lp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    class MySimpleGestureListeners extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getY() - e2.getY() > mMinDistance && Math.abs(velocityY) > mVelocityy) {
                if (mPosition < getChildCount()-1) {
                    showNext();
                    ++mPosition;
                }
            } else if (e2.getY() - e1.getY() > mMinDistance && Math.abs(velocityY) > mVelocityy) {
                if (mPosition > 0) {
                    showPrevious();
                    --mPosition;
                }
            }
            return true;
        }
    }

    private int mPosition = 0;
    private int mMinDistance = 50;
    private float mVelocityy = 20;
}

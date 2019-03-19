package com.example.testwifi.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.testwifi.R;


public class TestView extends View {
    private Bitmap bitmap;
    private double curPage = 0.1;

    private int state = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("11", "curPage.." + curPage);
            if (curPage >= 1) {
                state = 1;
            } else if (curPage <= 0) {
                state = 0;
            }
            if (state == 0) {
                curPage += 0.1;
            } else {
                curPage -= 0.1;
            }
            invalidate();
            handler.sendMessageDelayed(handler.obtainMessage(), 50);
        }
    };


    public TestView(Context context) {
        super(context);
        init(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_anim_test);
        handler.sendMessageDelayed(handler.obtainMessage(), 1000);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int h = bitmap.getHeight();
        int w = bitmap.getWidth();

        int cw = (int) (w * curPage);
        if (cw > w) cw = w;
        Rect rect = new Rect(0, 0, cw, h);
        Rect r = new Rect(50, 50, cw+50, h+50);
        canvas.drawBitmap(bitmap, rect, r, null);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("11", "TestView...dispatchTouchEvent..");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("11", "TestView...onTouchEvent..");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getWidth();
        int w1 = getMeasuredWidth();
        Log.e("11", "onMeasure....TestView...w.." + w);
        Log.e("11", "onMeasure....TestView...w1.." + w1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        int w = getWidth();
        Log.e("11", "onWindowFocusChanged....TestView...w.." + w);
    }

}

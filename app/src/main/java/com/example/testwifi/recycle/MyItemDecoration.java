package com.example.testwifi.recycle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    Paint.FontMetrics mFontMetrics;
    private int mDividerHeight = 30;
    int mNodeRadius = 5;

    public MyItemDecoration() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
//        mPaint.setStrokeWidth(5);

        mFontMetrics =mPaint.getFontMetrics();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        if (parent.getChildAdapterPosition(view) != 0) {
        outRect.top = mDividerHeight;
//        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count = parent.getChildCount();

        for (int i = 0; i < count; i++) {
            View v = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(v);

            int t = v.getTop() - mDividerHeight;
            int b = v.getTop();
            int l = parent.getPaddingLeft();
            int r = parent.getWidth() - parent.getPaddingRight();

            mPaint.setColor(Color.GREEN);
            c.drawRect(l, t, r, b, mPaint);

            int x = l + 10;
            int y = (int) (b - mFontMetrics.descent);
            String str = "第-->" + index;
            mPaint.setColor(Color.BLACK);
            Log.e("11", "x.." + x + "....y.." + y + "...b..." + b);
            Log.e("11", "t.." + t + l + "...b..." + b);
            c.drawText(str, x, y, mPaint);


        }
    }
}

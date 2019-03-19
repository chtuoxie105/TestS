package com.example.testwifi.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.testwifi.R;

public class TestViewGroup extends RelativeLayout {
    private Bitmap bitmap;
    //图片横向、纵向的格数
    private final int MESH_WIDTH = 20;
    private final int MESH_HEIGHT = 20;
    //图片的顶点数
    private final int VERTS_COUNT = (MESH_WIDTH + 1) * (MESH_HEIGHT + 1);
    //原坐标数组
    private final float[] staticVerts = new float[VERTS_COUNT * 2];
    //转换后的坐标数组
    private final float[] targetVerts = new float[VERTS_COUNT * 2];
    //水波宽度的一半
    private float rippleWidth = 100f;
    //水波扩散速度
    private float rippleSpeed = 15f;
    //水波半径
    private float rippleRadius;

    public TestViewGroup(Context context) {
        super(context);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_img_scale_anim);
        float w = bitmap.getWidth();
        float h = bitmap.getHeight();
        int index = 0;
        for (int i = 0; i <= MESH_HEIGHT; i++) {
            float y = i * h / MESH_HEIGHT;
            for (int j = 0; j <= MESH_WIDTH; j++) {
                float x = j * w / MESH_WIDTH;
                staticVerts[index * 2] = targetVerts[index * 2] = x;
                staticVerts[index * 2 + 1] = targetVerts[index * 2 + 1] = y;
                index += 1;
            }
        }
//        0 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40
//        1 3 5 7 9 11 13 15 17 19 21 23 25 27 29 31 33 35 37 39 41
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawBitmapMesh(bitmap, MESH_WIDTH, MESH_HEIGHT, targetVerts,
                0, null, 0, null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            showRipple(ev.getX(), ev.getY());
        }
        return super.dispatchTouchEvent(ev);
    }

    boolean isFinish = true;

    private void showRipple(final float x, final float y) {
        if (!isFinish) return;
        isFinish = false;
        int bmooWh = (int) getLength(bitmap.getWidth(), bitmap.getHeight());
        final int count = (int) ((bmooWh + rippleWidth) / rippleSpeed);
        CountDownTimer countDownTimer = new CountDownTimer(count * 10, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                rippleRadius = (count - millisUntilFinished / 10) * rippleSpeed;
                warp(x, y);
            }

            @Override
            public void onFinish() {
                isFinish = true;
            }
        };
        countDownTimer.start();
    }

    private void warp(float originX, float originY) {
        for (int i = 0; i < VERTS_COUNT * 2; i += 2) {
            float staticX = staticVerts[i];
            float staticY = staticVerts[i + 1];
            float length = getLength(staticX - originX, staticY - originY);
            if (length > rippleRadius - rippleWidth && length < rippleRadius + rippleWidth) {
                PointF point = getRipplePoint(originX, originY, staticX, staticY);
                targetVerts[i] = point.x;
                targetVerts[i + 1] = point.y;
            } else {
                //复原
                targetVerts[i] = staticVerts[i];
                targetVerts[i + 1] = staticVerts[i + 1];
            }
        }
        invalidate();
    }

    private PointF getRipplePoint(float originX, float originY, float staticX, float staticY) {
        float length = getLength(staticX - originX, staticY - originY);
        //偏移点与原点间的角度
        float angle = (float) Math.atan(Math.abs((staticY - originY) / (staticX - originX)));
        //计算偏移距离
        float rate = (length - rippleRadius) / rippleWidth;
        float offset = (float) Math.cos(rate) * 10f;
        float offsetX = offset * (float) Math.cos(angle);
        float offsetY = offset * (float) Math.sin(angle);
        //计算偏移后的坐标
        float targetX;
        float targetY;
        if (length < rippleRadius + rippleWidth && length > rippleRadius) {
            //波峰外的偏移坐标
            if (staticX > originX) {
                targetX = staticX + offsetX;
            } else {
                targetX = staticX - offsetX;
            }
            if (staticY > originY) {
                targetY = staticY + offsetY;
            } else {
                targetY = staticY - offsetY;
            }
        } else {
            //波峰内的偏移坐标
            if (staticX > originX) {
                targetX = staticX - offsetX;
            } else {
                targetX = staticX + offsetX;
            }
            if (staticY > originY) {
                targetY = staticY - offsetY;
            } else {
                targetY = staticY + offsetY;
            }
        }
        return new PointF(targetX, targetY);
    }


    /**
     * 根据宽高，获取对角线距离
     *
     * @param width
     * @param height
     * @return
     */
    private float getLength(float width, float height) {
        return (float) Math.sqrt(width * width + height * height);
    }
}

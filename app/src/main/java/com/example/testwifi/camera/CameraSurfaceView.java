package com.example.testwifi.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private int w, h;
    private int mCameraOption = Camera.CameraInfo.CAMERA_FACING_BACK;

    public CameraSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        surfaceHolder = getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    public void setCallBack() {
        try {
            camera = Camera.open(mCameraOption);
            setCameraParams();
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        surfaceHolder.addCallback(this);
        camera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera == null) return;
        camera.stopPreview();
        camera.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            zoomScale();
        }


        return super.onTouchEvent(event);
    }

    int i = 3;

    private void zoomScale() {
        i++;
        Camera.Parameters parameters = camera.getParameters();
        parameters.setZoom(i > parameters.getMaxZoom() ? i = parameters.getMaxZoom() : i);
        camera.setParameters(parameters);
    }

    private void setCameraParams() {
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> l = parameters.getSupportedPictureSizes();
        Camera.Size s = getSize(l);
        if (s != null) {
            parameters.setPictureSize(s.width, s.height);
        }
        parameters.setJpegQuality(100);
        if (parameters.getSupportedFocusModes().contains(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }
        camera.cancelAutoFocus();
        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);
    }

    private Camera.Size getSize(List<Camera.Size> l) {
        Camera.Size result = null, normalSize = null;
        float screenRatio = w / h;
        for (Camera.Size s : l) {
            float sRatio = s.width / s.height;
            if (sRatio == 4f / 3) {
                normalSize = s;
            }
            if (sRatio - screenRatio == 0) {
                result = s;
                break;
            }
        }
        if (result == null) {
            result = normalSize;
        }
        return result;
    }

    public void turmCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;//取消原来摄像头
        }
        int curOption = mCameraOption;
        if (curOption == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraOption = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraOption = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        try {
            camera = Camera.open(mCameraOption);
            camera.setPreviewDisplay(surfaceHolder);
            camera.setDisplayOrientation(90);
            setCameraParams();
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

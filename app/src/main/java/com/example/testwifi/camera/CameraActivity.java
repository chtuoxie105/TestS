package com.example.testwifi.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.service.media.CameraPrewarmService;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.testwifi.R;

public class CameraActivity extends AppCompatActivity {
    CameraSurfaceView cameraSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraSurfaceView = findViewById(R.id.test_surface_view);
        findViewById(R.id.test_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHavePermissions()) {
                    request();
                } else {
                    cameraSurfaceView.setCallBack();
                }
            }
        });
        findViewById(R.id.test_take_turn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraSurfaceView.turmCamera();
            }
        });
    }

    private void request() {
        String[] a = {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, a, 200);
    }

    private boolean isHavePermissions() {
        String[] a = {Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this, a[0]) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            boolean isget = true;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    isget = false;
                }
            }
        }
    }

}

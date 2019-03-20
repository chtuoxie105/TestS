package com.example.testwifi;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.testwifi.camera.CameraActivity;
import com.example.testwifi.camera.PhoneActivity;
import com.example.testwifi.nestscroll.NestScrollActivity;
import com.example.testwifi.nfc.NFCIdCardActivity;
import com.example.testwifi.page.FilterActivity;
import com.example.testwifi.page.PageListActivity;
import com.example.testwifi.page.PageViewActivity;
import com.example.testwifi.paint.PaintActivity;
import com.example.testwifi.recycle.RecycleTActivity;
import com.example.testwifi.task.NewTaskActivity;
import com.example.testwifi.task.ScrollActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.test_btn_1).setOnClickListener(this);
        findViewById(R.id.test_btn_2).setOnClickListener(this);
        findViewById(R.id.test_btn_3).setOnClickListener(this);
        findViewById(R.id.test_btn_4).setOnClickListener(this);
        findViewById(R.id.test_btn_5).setOnClickListener(this);
        findViewById(R.id.test_btn_6).setOnClickListener(this);
        findViewById(R.id.test_btn_7).setOnClickListener(this);
        findViewById(R.id.test_btn_8).setOnClickListener(this);
        findViewById(R.id.test_btn_9).setOnClickListener(this);
        findViewById(R.id.test_btn_10).setOnClickListener(this);
        findViewById(R.id.test_btn_11).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        request();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {
            case R.id.test_btn_1:
                intent = new Intent(TestActivity.this, PageListActivity.class);
                break;
            case R.id.test_btn_2:
                intent = new Intent(TestActivity.this, PageViewActivity.class);
                break;
            case R.id.test_btn_3:
                intent = new Intent(TestActivity.this, FilterActivity.class);
                break;
            case R.id.test_btn_4:
                intent = new Intent(TestActivity.this, RecycleTActivity.class);
                break;
            case R.id.test_btn_5:
                intent = new Intent(TestActivity.this, PaintActivity.class);
                break;
            case R.id.test_btn_6:
                intent = new Intent(TestActivity.this, NestScrollActivity.class);
                break;
            case R.id.test_btn_7:
                intent = new Intent(TestActivity.this, NewTaskActivity.class);
                break;
            case R.id.test_btn_8:
                intent = new Intent(TestActivity.this, ScrollActivity.class);
                break;
            case R.id.test_btn_9:
                intent = new Intent(TestActivity.this, NFCIdCardActivity.class);
                break;
            case R.id.test_btn_10:
                intent = new Intent(TestActivity.this, CameraActivity.class);
                break;
            case R.id.test_btn_11:
                intent = new Intent(TestActivity.this, PhoneActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }

    PermissionUtil permissionUtil;

    private void request() {
        permissionUtil = new PermissionUtil(this);
        permissionUtil.add(Manifest.permission.READ_PHONE_STATE);
        permissionUtil.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionUtil.add(Manifest.permission.CAMERA);
        permissionUtil.add(Manifest.permission.CALL_PHONE);
        permissionUtil.add(Manifest.permission.READ_CONTACTS);
        permissionUtil.add(Manifest.permission.SEND_SMS);
        permissionUtil.add(Manifest.permission.READ_SMS);
        permissionUtil.add(Manifest.permission.RECEIVE_SMS);
        permissionUtil.requestPermission(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}

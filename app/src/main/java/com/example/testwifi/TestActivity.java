package com.example.testwifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.testwifi.camera.CameraActivity;
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent=null;
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
        }
        startActivity(intent);
    }
}

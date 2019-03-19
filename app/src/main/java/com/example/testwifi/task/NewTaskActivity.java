package com.example.testwifi.task;

import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.testwifi.R;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("11","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("11","onResume");
    }
}

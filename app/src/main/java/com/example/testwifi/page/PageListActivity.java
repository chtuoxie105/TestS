package com.example.testwifi.page;

import android.Manifest;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.testwifi.R;

public class PageListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_list);
//        recyclerView = findViewById(R.id.test_page_recy_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            reportFullyDrawn();
        }
//        ViewModelProvider viewModelProvider;
//        viewModelProvider.get()
    }

}

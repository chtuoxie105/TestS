package com.example.testwifi.recycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.testwifi.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleTActivity extends AppCompatActivity {
    RecyclerView recyclerView = null;
    MyRecyAdapter recyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_t);
        recyclerView = findViewById(R.id.test_recycleview);
        recyclerView.addItemDecoration(new MyItemDecoration());
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);

        recyclerView.setAdapter(recyAdapter = new MyRecyAdapter());
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getData() {
        List<String> l = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            l.add("msg..." + i);
        }

        recyAdapter.setData(l);
        recyAdapter.notifyDataSetChanged();
    }
}

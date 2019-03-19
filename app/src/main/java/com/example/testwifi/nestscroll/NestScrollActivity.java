package com.example.testwifi.nestscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testwifi.R;
import com.example.testwifi.recycle.MyRecyAdapter;

import java.util.ArrayList;
import java.util.List;

public class NestScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_scroll);
        NestScroolLy nestScroolLy = findViewById(R.id.test_nest_root_view);
        NestScrollAdapter nestScrollAdapter = new NestScrollAdapter();
//        MyRecyAdapter nestScrollAdapter = new MyRecyAdapter();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("item.." + i);
        }

        nestScroolLy.setAdapter(nestScrollAdapter);
        nestScrollAdapter.setData(list);
        nestScrollAdapter.notifyDataSetChanged();
    }
}

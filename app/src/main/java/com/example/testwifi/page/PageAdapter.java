package com.example.testwifi.page;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.testwifi.R;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageVh> {

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public PageVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_adapter_layout, parent, false);
//        View v = View.inflate(parent.getContext(), R.layout.page_adapter_layout, parent);
        return new PageVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PageVh holder, int position) {
//        if (position % 2 != 0) {
//            holder.ly.setBackgroundColor(Color.RED);
//        } else {
//            holder.ly.setBackgroundColor(Color.WHITE);
//        }
        Log.e("11", "positon.." + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PageVh extends RecyclerView.ViewHolder {
        LinearLayout ly;

        public PageVh(View itemView) {
            super(itemView);
            ly = itemView.findViewById(R.id.page_adapter_ly);
        }
    }
}

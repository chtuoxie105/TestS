package com.example.testwifi.recycle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testwifi.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyAdapter extends RecyclerView.Adapter<MyRecyAdapter.MyRecyAdapterVh> {
    private List<String> data = new ArrayList<>();

    public void setData(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecyAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recycyle_item_layout,parent,false);
//        View v = View.inflate(parent.getContext(), R.layout.adapter_recycyle_item_layout, null);
        return new MyRecyAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyAdapterVh holder, int position) {

        String msg = data.get(position);
        holder.tv.setText(msg);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyRecyAdapterVh extends RecyclerView.ViewHolder {
        TextView tv;

        public MyRecyAdapterVh(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.adapter_test_item_tv);
        }
    }
}

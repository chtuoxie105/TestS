package com.example.testwifi.nestscroll;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testwifi.R;

import java.util.ArrayList;
import java.util.List;

public class NestScrollAdapter extends RecyclerView.Adapter<NestScrollAdapter.NestScrollAdapterVh> {
    private List<String> data = new ArrayList<>();

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NestScrollAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.adapter_recycyle_item_layout, null);
        return new NestScrollAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NestScrollAdapterVh holder, int position) {
        holder.tvItem.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NestScrollAdapterVh extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public NestScrollAdapterVh(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.adapter_test_item_tv);
        }
    }
}

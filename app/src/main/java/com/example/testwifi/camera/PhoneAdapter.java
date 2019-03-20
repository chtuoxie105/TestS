package com.example.testwifi.camera;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testwifi.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter  extends RecyclerView.Adapter<PhoneAdapter.PhoneAdapterVh>{
    private List<PhonePeopleBean> data = new ArrayList<>();

    public void setData(List<PhonePeopleBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PhoneAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recycyle_item_layout, parent, false);
//        View v = View.inflate(parent.getContext(), R.layout.adapter_recycyle_item_layout, null);
        return new PhoneAdapterVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapterVh holder, int position) {
        PhonePeopleBean msg = data.get(position);
        holder.tv.setText(msg.getName() + "----" + msg.getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PhoneAdapterVh extends RecyclerView.ViewHolder {
        TextView tv;

        public PhoneAdapterVh(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.adapter_test_item_tv);
        }
    }
}

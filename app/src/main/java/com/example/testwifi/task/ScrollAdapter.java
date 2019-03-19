package com.example.testwifi.task;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testwifi.R;

import java.util.ArrayList;

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.Vh> implements Handler.Callback {
    private RecyclerView recyclerView;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> allList = new ArrayList<>();
    private int MAXSIZE = 5, mMsgWhat = 1;
    private int mCurPosition = 0;
    private Handler handler;

    public ScrollAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        handler = new Handler(this);
    }

    public void setList(ArrayList<String> l) {
        allList.clear();
        list.clear();
        allList = l;
        for (int i = 0; i < MAXSIZE; i++) {
            list.add(l.get(i));
        }
        mCurPosition = list.size() - 1;
    }

    public void startScroll() {
        handler.sendMessageDelayed(handler.obtainMessage(mMsgWhat), 1000);
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.adapter_recycyle_item_layout, null);
        return new Vh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        String txt = list.get(position);
        holder.tvItem.setText(txt);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg != null) {
            list.remove(0);
            notifyDataSetChanged();
//            recyclerView.scrollToPosition(0);
            ++mCurPosition;
            if (mCurPosition == allList.size()) {
                mCurPosition = 0;
            }
            list.add(allList.get(mCurPosition));
            notifyItemChanged(list.size() - 1);
            handler.sendMessageDelayed(handler.obtainMessage(mMsgWhat), 1000);
            return true;
        }
        Log.e("11","msg..."+msg.what);
        return false;
    }

    class Vh extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public Vh(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.adapter_test_item_tv);
        }
    }

    public void onStop() {
        handler.removeMessages(mMsgWhat);
    }

}

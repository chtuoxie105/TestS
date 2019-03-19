package com.example.testwifi.recycle;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsRecyAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    private List<T> list = new ArrayList<>();

    public void setList(List<T> list) {
        if (list != null) {
            this.list = list;
        }
    }

    public void addList(List<T> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public T getOnItem(int position) {
        return list.get(position);
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}

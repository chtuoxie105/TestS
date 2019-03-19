package com.example.testwifi.page;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.testwifi.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollRecyclerView extends RecyclerView {
    private PageAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int mMaxSize = 0, mMinSize = 0;

    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(linearLayoutManager = new LinearLayoutManager(context));
        mMaxSize = (int) context.getResources().getDimension(R.dimen.item_max);
        mMinSize = (int) context.getResources().getDimension(R.dimen.item_min);
        setAdapter(mAdapter = new PageAdapter());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item..:" + i);
        }
        mAdapter.getList().addAll(list);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0) {
                    int count = linearLayoutManager.getChildCount();
                    for (int i = 0; i < count; i++) {
                        setItemHeight(i, i == 0 ? mMaxSize : mMinSize);
                    }
                } else {
                    int p = 0;
                    int count = linearLayoutManager.getChildCount();
                    if (dy > 0) {
                        for (int i = 0; i < count; i++) {
                            View v = linearLayoutManager.getChildAt(i);
                            int h = v.getHeight();
                            if (h < mMaxSize && h >= mMinSize) {
                                p = i;
                                break;
                            }
                        }
                    } else if (dy < 0) {
                        int start = count - 1;
                        for (int i = start; i > 0; i--) {
                            View v = linearLayoutManager.getChildAt(i);
                            int h = v.getHeight();
                            if (h <= mMaxSize && h > mMinSize) {
                                p = i;
                                break;
                            }
                        }
                    }

                    int currentPosition = ((RecyclerView.LayoutParams)
                            recyclerView.getChildAt(p).getLayoutParams()).getViewAdapterPosition();
                    if (currentPosition == 0) {
                        setItemHeight(0, mMaxSize);
                        return;
                    }
                    addItemHeight(p, dy < 0 ? mMinSize : mMaxSize, dy);
                }
            }
        });
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }


    private boolean setItemHeight(int position, int height) {
        View v = linearLayoutManager.getChildAt(position);
        if (v != null) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) v.getLayoutParams();
            if (lp == null) {
                lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, height);
            }
            lp.height = height;
            v.setLayoutParams(lp);
            return true;
        }
        return false;
    }

    private boolean addItemHeight(int position, int height, int dy) {
        View v = linearLayoutManager.getChildAt(position);
        if (v != null) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) v.getLayoutParams();
            if (lp == null) {
                lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, height);
            }
            lp.height += dy;
//            if (position == 0) {
//                lp.height = mMaxSize;
//            }
            if (lp.height > mMaxSize) {
                lp.height = mMaxSize;
            }
            if (lp.height < mMinSize) {
                lp.height = mMinSize;
            }
            v.setLayoutParams(lp);
            return true;
        }
        return false;
    }
}

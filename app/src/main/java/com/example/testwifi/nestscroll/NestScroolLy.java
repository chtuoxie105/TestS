package com.example.testwifi.nestscroll;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.testwifi.R;
import com.example.testwifi.recycle.MyItemDecoration;

public class NestScroolLy extends FrameLayout implements NestedScrollingParent, NestedScrollingChild {
    private NestedScrollingParentHelper nestedScrollingParentHelper;
    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private View topView, bottomView;
    private RecyclerView recyclerView;
    private int height = 0, stateHeight = 0;

    public NestScroolLy(@NonNull Context context) {
        super(context, null);
    }

    public NestScroolLy(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NestScroolLy(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void log(String msg) {
        Log.e("NestScroolLy", msg);
    }

    private void init(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        stateHeight = resources.getDimensionPixelSize(resourceId);


        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = 0;
        FrameLayout.LayoutParams lp = (LayoutParams) topView.getLayoutParams();
        height = lp.height;
//        topView.setLayoutParams(lp);
        h = height;
        log("onMeasure>:" + lp.height + " " + lp.topMargin);
        lp = (LayoutParams) recyclerView.getLayoutParams();
        lp.height = getMeasuredHeight();
        lp.topMargin = height;
        recyclerView.setLayoutParams(lp);
        h += lp.height;

        log("onMeasure>:" + lp.height + " " + lp.topMargin + "..margbottom.." + lp.bottomMargin);
        lp = (LayoutParams) bottomView.getLayoutParams();
        lp.topMargin = height + getMeasuredHeight();
        bottomView.setLayoutParams(lp);
        h += lp.height;
        log("onMeasure>:" + lp.height + " " + lp.topMargin);
        log("onMeasure>hhh:" + h);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.test_nestscroll_top);
        recyclerView = findViewById(R.id.test_nestscroll_recycle);
        bottomView = findViewById(R.id.test_nestscroll_bottom);
        recyclerView.addItemDecoration(new MyItemDecoration());
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!recyclerView.canScrollVertically(1)) {
//                    log("direction 1: false");//滑动到底部
//                    isScrollBottom = true;
//                } else {
//                    isScrollBottom = false;
//                }
//                if (!recyclerView.canScrollVertically(-1)) {
//                    log("direction -1: false");//滑动到顶部
//                }
//            }
//        });
    }

    boolean isScrollBottom = false;

    private boolean isRecycleScrollToBottom() {
        return isScrollBottom;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return nestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
        log("setNestedScrollingEnabled...");
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
    }


    @Override
    public void stopNestedScroll() {
        nestedScrollingChildHelper.stopNestedScroll();
    }


    @Override
    public void onStopNestedScroll(View child) {
        log("onStopNestedScroll.." + getScrollY());
        if (getScrollY() < height && getScrollY() > 0) {
            if (getScrollY() >= height / 2) {
                scrollTo(0, height);
            } else {
                scrollTo(0, 0);
            }
            recyclerView.scrollToPosition(0);
        } else {
//            if (recyclerView.canScrollVertically(-1)) {
//                nestedScrollingParentHelper.onStopNestedScroll(child);
//            } else if (recyclerView.canScrollVertically(1)) {
//                nestedScrollingParentHelper.onStopNestedScroll(child);
//            } else if (getScrollY() > topView.getHeight()) {
//                if (getScrollY() < topView.getHeight() + bottomView.getHeight() / 2) {
//                    scrollTo(0, topView.getHeight() + recyclerView.getHeight());
//                } else {
//                    scrollTo(0, topView.getHeight() + recyclerView.getHeight() + bottomView.getHeight());
//                }
//            } else {
//                recyclerView.scrollToPosition(0);
//            }

            if (isScrollRecycle()) {
                    nestedScrollingParentHelper.onStopNestedScroll(child);
            } else {
                recyclerView.scrollToPosition(0);
            }
        }
//            if (!isShowTopview) {
// nestedScrollingParentHelper.onStopNestedScroll(child);
//            } else {
//                if (getScrollY() == topView.getHeight()) {
//                    if (isFlingGoneTopView) {
//                        scrollTo(0, height);
//                        isShowTo pview = false;
//                    } else {
//                        scrollTo(0, 0);
//                        isShowTopview = true;
//                    }
//                }
//            }


//        if (isScrollRecycle()) {
//            nestedScrollingParentHelper.onStopNestedScroll(child);
//        } else {
//            if (getScrollY() < height && getScrollY() > 0) {
//                if (getScrollY() < height / 2) {
//                    scrollTo(0, 0);
//                } else {
//                    scrollTo(0, height);
//                }
//            }
//            recyclerView.scrollToPosition(0);
//        }
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        log("dy.." + dy + "...scrolly.." + getScrollY());
        if (getScrollY() < height && dy > 0) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
        if (dy < 0 && getScrollY() > 0 && getScrollY() <= height) {
            if (!isScrollRecycle()) {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
        if (!recyclerView.canScrollVertically(1)) {
            int h = topView.getHeight() + bottomView.getHeight();
            if (getScrollY() <= h && getScrollY() >= 0) {
                int scy = dy;
                if (getScrollY() + dy >= h) {
                    scy = h - getScrollY();
                }
                consumed[1] = scy;
                scrollBy(0, scy);

            }
        }
//        if (dy < 0 && getScrollY() >= topView.getHeight()) {
//            consumed[1] = dy;
//            scrollBy(0, dy);
//        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        scrollBy(0, dyConsumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        log("matchInfoAdapter....onNestedPreFling velocityY=" + velocityY);
        if (getScrollY() < topView.getHeight()) {
            if (velocityY < -1000 && !isScrollRecycle()) {
                scrollTo(0, 0);
                return true;
            } else if (velocityY > 1000) {
                scrollTo(0, topView.getHeight());
                return true;
            }
        }
        if (!isScrollRecycle() && getScrollY() == topView.getHeight() && velocityY < -1000) {
            scrollTo(0, 0);
            return true;
        }
        log("onNestedFling......");
//        if (!isScrollRecycle()) {
//            if (getScrollY() <= height) {
//                if (velocityY < -1000) {
//                    if (!isScrollRecycle()) {
//                        scrollTo(0, 0);
//                    }
//                } else if (velocityY > 1000) {
//                    scrollTo(0, height);
//                    recyclerView.scrollToPosition(0);
//                }
//                return true;
//            }
//        }

        return super.onNestedFling(target, velocityX, velocityY, consumed);

    }

    private boolean isScrollRecycle() {
        boolean isScrollrecycle = false;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
            int firstPositon = lm.findFirstVisibleItemPosition();
            isScrollrecycle = firstPositon > 0;
        }
        log("isScrollrecycle..:" + isScrollrecycle);
        return isScrollrecycle;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
}

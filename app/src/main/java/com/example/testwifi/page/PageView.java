package com.example.testwifi.page;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PageView extends ScrollView {
    private Context mContext;
    private int mConut = 0;
    private boolean isLoadMore = false, isScrollVisibFoot = false;

    public PageView(Context context) {
        super(context);
        init(context);
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    LinearLayout lyRoot, lyCenter, lyFoot;
    private int heigth, width;

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void init(Context context) {
        mContext = context;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        heigth = dm.heightPixels - getStatusBarHeight(context);
        width = dm.widthPixels;
        gestureDetector = new GestureDetector(context, new mySimGestureListener());


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lyRoot = new LinearLayout(context);
        lyRoot.setOrientation(LinearLayout.VERTICAL);
        addView(lyRoot, lp);

        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lyCenter = new LinearLayout(context);
        lyCenter.setOrientation(LinearLayout.VERTICAL);
        lyRoot.addView(lyCenter, lp);
        addData();
        addFpptView();
    }

    public void addData() {
        Log.e("11", "heui.." + heigth);
        TextView tv = new TextView(mContext);
        tv.setText("44444");
        tv.setWidth(width);
        tv.setHeight(heigth);
        tv.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, heigth);
        lp.gravity = Gravity.CENTER;
        lyCenter.addView(tv, lp);

        tv = new TextView(mContext);
        tv.setText("5555");
        tv.setHeight(heigth);
        tv.setWidth(width);
        tv.setBackgroundColor(Color.WHITE);
        lp = new LinearLayout.LayoutParams(width, heigth);
        lp.gravity = Gravity.CENTER;
        lyCenter.addView(tv, lp);


        tv = new TextView(mContext);
        tv.setText("66666666");
        tv.setWidth(width);
        tv.setHeight(heigth);
        tv.setBackgroundColor(Color.GREEN);
        lp = new LinearLayout.LayoutParams(width, heigth);
        lp.gravity = Gravity.CENTER;
        lyCenter.addView(tv, lp);
        mConut = lyCenter.getChildCount();

    }

    private void addFpptView() {
        lyFoot = new LinearLayout(mContext);
        lyFoot.setBackgroundColor(Color.RED);
        lyFoot.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lyRoot.addView(lyFoot, lp);


        TextView tv = new TextView(mContext);
        tv.setText("加载更多");
        tv.setWidth(width);
        tv.setHeight(heigth);
        lp = new LinearLayout.LayoutParams(width, 300);
        lp.gravity = Gravity.CENTER;
        lyFoot.addView(tv, lp);
        lyFoot.setVisibility(GONE);
    }

    private long minTouchTime = 2000;
    private long firstTouTime = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            firstTouTime = System.currentTimeMillis();
        }
        Log.e("11", "action.." + action + "...MotionEvent..." + event.getAction() + "..dely.." + delY);
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            Log.e("11","onTouchEvent...ACTION_UP");
            if (lyFoot.getVisibility() == View.VISIBLE) {
                if (lyFoot.getHeight() < 300) {
                    int h = lyFoot.getHeight();
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lyFoot.getLayoutParams();
                    lp.height = 0;
                    lyFoot.setLayoutParams(lp);
                    scrollTo(0, mPositon * heigth);
                    lyFoot.setVisibility(GONE);
                    return true;
                }
                if (!isLoadMore) {
                    isLoadMore = true;
                    startFootScanlAnimator(lyFoot.getHeight());
                    delY = 0;
                }
                return true;
            }
            if (action == 3 && mPositon == lyCenter.getChildCount() - 1) {
                if (delY < 0) {
                    if (Math.abs(delY) < 300) {
                        int h = 300 - Math.abs(delY);
                        Log.e("11", "h....;" + h);
                        scrollBy(0, -h);
                    } else {
                        int h = Math.abs(delY) - 300;
                        scrollBy(0, h);
                    }
                }
            }
            if (action == 2) {
                if (isLoadMore) {
                    if (mPositon < lyCenter.getChildCount() - 1) {
                        scrollBy(0, (int) -delY);
                    } else {
                        if (delY < 0) {
                            if (Math.abs(delY) < 300) {
                                int h = 300 - Math.abs(delY);
                                Log.e("11", "h....;" + h);
                                scrollBy(0, -h);
                            } else {
                                int h = Math.abs(delY) - 300;
                                scrollBy(0, h);
                            }
                        }
                    }
                } else {
                    if (Math.abs(delY) > heigth / 3) {
                        if (delY < 0) {
                            up();
                        } else {
                            next();
                        }
                    } else {
                        if (mPositon < lyCenter.getChildCount() - 1) {
                            scrollBy(0, (int) -delY);
                        } else {
                            int h = 300 - Math.abs(delY);
                            if (isLoadMore) {
                                if (mPositon == lyCenter.getChildCount() - 1) {
                                    if (delY < 0) {
                                        if (h < 0) {
                                            scrollBy(0, -h);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            delY = 0;
            if (System.currentTimeMillis() - firstTouTime < minTouchTime) {

            }

        }

        return true;
    }

    GestureDetector gestureDetector;
    private int delY = 0;
    private int mPositon = 0;

    private int action = 0;

    class mySimGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("11", "onSingleTapConfirmed...暂停");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("11", "onDoubleTap.......双击");
            action = 1;
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("11", "distanceX--->" + distanceX + "...distanceY--->" + distanceY);
            delY += (int) distanceY;
            if (!isLoadMore) {
                if ((mPositon == lyCenter.getChildCount() - 1 && distanceY > 0) || (lyFoot.getVisibility() == View.VISIBLE)) {
//
//                    }
//
//            if (lyFoot.getVisibility() == View.VISIBLE ||
//                    (mPositon == lyCenter.getChildCount() - 1 && distanceY > 0 )) {
                    lyFoot.setVisibility(VISIBLE);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lyFoot.getLayoutParams();
                    lp.height += distanceY;
                    if (lp.height > heigth / 2) {
                        lp.height += distanceY / 50;
                    } else if (lp.height > 300) {
                        lp.height += distanceY / 10;
                    }
                    lyFoot.setLayoutParams(lp);
                }
            }
            action = 2;
            scrollBy(0, (int) distanceY);
//            if(distanceY>0){
//                isScrollVisibFoot = true;
//            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.e("11", "velocityX--->" + velocityX + "...velocityY--->" + velocityY);
            if (e1.getY() - e2.getY() > mMinDistance && Math.abs(velocityY) > mVelocityy) {
                next();
            } else if (e2.getY() - e1.getY() > mMinDistance && Math.abs(velocityY) > mVelocityy) {
                up();
            }
            action = 3;
            return true;
        }
    }

    ;

    private int mMinDistance = 50;
    private float mVelocityy = 20;

    private void next() {
        if (mPositon < mConut - 1) {
            ++mPositon;
            int h = heigth * mPositon;
            scrollTo(0, h);
        }
    }

    private void up() {
        if (isScrollVisibFoot && isLoadMore) {
            scrollTo(0, mPositon * heigth);
            isScrollVisibFoot = false;
            delY = 0;
            return;
        }
        if (mPositon > 0) {
            --mPositon;
            int h = heigth * mPositon;
            scrollTo(0, h);
        }
    }


    private void startFootScanlAnimator(int h) {
        isScrollVisibFoot = true;
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(h, 300);
        valueAnimator.setDuration(150);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curHeight = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lyFoot.getLayoutParams();
                lp.height = curHeight;
                lyFoot.setLayoutParams(lp);
                if (curHeight == 300) {
//                    lyFoot.setVisibility(GONE);
//                    isLoadMore = false;
                }
            }
        });
        valueAnimator.start();
    }
}

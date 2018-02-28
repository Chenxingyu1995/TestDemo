package com.example.duangniu000.test2.RefreshLayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duangniu000.test2.Util.ToastUtil;


public class RefreshLayout extends ViewGroup {

    protected View titleView;
    protected View contentView;
    protected View footView;


    /**
     * 最低刷新距离
     **/
    protected int min_distance = 0;

    /**
     * 最大拖拽距离
     **/
    protected int max_distance;

    /**
     * 是否启用最大拖拽距离
     **/
    protected boolean enabledMaxDistance = false;

    public void setEnabledMaxDistance(boolean enabledMaxDistance) {
        this.enabledMaxDistance = enabledMaxDistance;
    }

    /**
     * 滑动阻尼系数
     **/
    protected float damp = 2.5f;

    /**
     * 是否开启下拉刷新
     **/
    protected boolean enabled_refresh = true;

    /**
     * 是否开启加载更多
     **/
    protected boolean enabled_load = true;


    /**
     * 是否进行拦截
     **/
    protected boolean isDispatch = false;

    /**
     * 是否进行刷新
     **/
    protected boolean isRefresh = false;

    /**
     * 刷新动画状态
     **/
    protected int refresh_stuta = NOT_STARTED;

    protected final static int START_ING = 1;

    protected final static int NOT_STARTED = 2;

    /**
     * 滚动方向
     **/
    protected refresh_type type;

    protected enum refresh_type {
        UP,
        DOWN,
        NULL
    }

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        titleView = getChildAt(0);
        contentView = getChildAt(1);
        footView = getChildAt(2);
        titleView.layout(0, -titleView.getMeasuredHeight(), getWidth(), 0);
        footView.layout(0, getHeight(), getWidth(), getHeight() + footView.getMeasuredHeight());
        contentView.layout(0, 0, getWidth(), contentView.getMeasuredHeight());
        setMinSlipDistance();
    }

    /**
     * 判断是否下拉到刷新状态
     **/
    protected int isRefreshDown(float offset) {

        if (refresh_stuta == START_ING) {
            offset = 0;
            isRefresh = false;
            isDispatch = false;
        } else {
            isRefresh = Math.abs(offset) > min_distance;
            if (isRefresh) {
                setRefreshText("松开刷新...");
            } else {
                setRefreshText("下拉刷新...");
            }
            isDispatch = true;
        }
        return enabledMaxDistance((int) offset);
    }

    /**
     * 判断是否上拉到加载状态
     **/
    protected int isRefreshUp(float offset) {
        if (refresh_stuta == START_ING) {
            offset = 0;
            isRefresh = false;
            isDispatch = false;
        } else {
            isRefresh = Math.abs(offset) > min_distance;
            if (isRefresh) {
                setLoadMoreText("松手加载...");
            } else {
                setLoadMoreText("下拉加载...");
            }
            isDispatch = true;
        }
        return enabledMaxDistance((int) offset);
    }

    protected int enabledMaxDistance(int offset) {

        if (enabledMaxDistance && Math.abs(offset) > max_distance) {

            if (max_distance <= 0) {
                throw new IllegalArgumentException("max_distance must be greater than 0");
            }

            if (offset < 0) {
                offset = -max_distance;
            } else {
                offset = max_distance;
            }
        }
        return offset;

    }

    /**
     * 松手状态判断是否进行加载或者刷新
     **/
    protected void actionUp() {
        //是否需要刷新
        if (isRefresh) {
            smoothScrollRefresh();
        } else {
            //如不，判断是否拦截事件,对View 的位置进行还原
            if (isDispatch) {
                restoreLayout();
            }
        }
        //还原状态
        isRefresh = false;
        isDispatch = false;
    }

    /**
     * 是否开始滚动
     **/
    protected boolean canScroll(int type) {

        if (refresh_stuta == START_ING) {
            return true;
        }
        return contentView.canScrollVertically(type);
    }

    /**
     * 滚动到显示Header或者Foot 位置，表明开始刷新或加载状态
     **/
    protected void smoothScrollRefresh() {
        refresh_stuta = START_ING;
        ValueAnimator animator;
        if (type == refresh_type.DOWN) {
            setRefreshText("正在刷新...");
            animator = ValueAnimator.ofInt(getScrollY(), -min_distance);
        } else if (type == refresh_type.UP) {
            setLoadMoreText("正在加载...");
            animator = ValueAnimator.ofInt(getScrollY(), min_distance);
        } else {
            animator = ValueAnimator.ofInt(getScrollY(), 0);
        }
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (type == refresh_type.DOWN) {
                    if (onRefreshListener != null) {
                        onRefreshListener.onRefresh(RefreshLayout.this);
                    }
                } else if (type == refresh_type.UP) {
                    if (onRefreshListener != null) {
                        onRefreshListener.onLoadMore(RefreshLayout.this);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(0, value);
            }
        });
        animator.start();
    }

    /**
     * 还原
     **/
    protected void restoreLayout() {
        ValueAnimator animator = ValueAnimator.ofInt(getScrollY(), 0);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                refresh_stuta = NOT_STARTED;
                if (type == refresh_type.DOWN) {
                    setRefreshText("下拉刷新...");
                } else if (type == refresh_type.UP) {
                    setLoadMoreText("上拉加载...");
                }
                type = refresh_type.NULL;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(0, value);
            }

        });
        animator.start();
    }

    //加载完成
    public void refreshComplete() {
        if (type == refresh_type.DOWN) {
            setRefreshText("刷新完成");
        } else if (type == refresh_type.UP) {
            setLoadMoreText("加载完成");
        } else {
            ToastUtil.showToast(getContext(), "刷新错误");
        }

        if (ViewCompat.isAttachedToWindow(this)) {
            restoreLayout();
        }
    }

    public void setRefreshText(String string) {
        if (titleView instanceof TextView) {
            if (!string.equals(((TextView) titleView).getText())) {
                ((TextView) titleView).setText(string);
            }
        }
    }

    public void setLoadMoreText(String string) {
        if (footView instanceof TextView) {
            if (!string.equals(((TextView) footView).getText())) {
                ((TextView) footView).setText(string);
            }
        }
    }

    /**
     * 手指开始的位置
     **/
    protected float startY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offset = (startY - ev.getY()) / damp;

                if (offset < 0 && !canScroll(-1) && enabled_refresh) {
                    type = refresh_type.DOWN;
                    return true;
                }

                if (offset > 0 && !canScroll(1) && enabled_load) {
                    type = refresh_type.UP;
                    return true;
                }
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float offset = (startY - ev.getY()) / damp;

                if (offset < 0 && !canScroll(-1) && enabled_refresh) {
                    type = refresh_type.DOWN;
                    scrollTo(0, isRefreshDown(offset));
                }

                if (offset > 0 && !canScroll(1) && enabled_load) {
                    type = refresh_type.UP;
                    scrollTo(0, isRefreshUp(offset));
                }

                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
        }
        return super.onTouchEvent(ev);
    }

    protected OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    public interface OnRefreshListener {

        void onRefresh(RefreshLayout layout);

        void onLoadMore(RefreshLayout layout);
    }

    public void setEnabledRefresh(boolean enabled) {
        this.enabled_refresh = enabled;
    }

    public void setEnabledloadMore(boolean enabled) {
        this.enabled_load = enabled;
    }

    public void setDamp(float damp) {
        if (damp < 2.5f) {
            damp = 2.5f;
        }
        if (damp > 10f) {
            damp = 10f;
        }
        this.damp = damp;
    }

    protected void setMinSlipDistance() {
        min_distance = titleView.getMeasuredHeight();
        if (min_distance == 0) {
            min_distance = 100;
        }
    }

}

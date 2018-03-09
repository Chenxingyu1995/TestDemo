package com.example.duangniu000.test2.CoustomView;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duangniu000.test2.R;

public class CoustomRefreshLayout extends ViewGroup {

    private View headerView;

    private View contentView;


    private float damp = 50f;

    public CoustomRefreshLayout(Context context) {
        this(context, null);
    }

    public CoustomRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoustomRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.expend_header, this, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        headerView = getChildAt(0);
        headerView.layout(0, 0, getWidth(), headerView.getMeasuredHeight());
        try {
            contentView = getChildAt(1);
            contentView.layout(0, headerView.getMeasuredHeight(), getWidth(), getHeight());
        } catch (Exception e) {

        }

    }

    /**
     * 手指开始的位置
     **/
    protected float startY = 0;

    public boolean enabled_refresh = true;
    public boolean enabled_load = true;

    public void setEnabled_load(boolean enabled_load) {
        this.enabled_load = enabled_load;
    }

    public void setEnabled_refresh(boolean enabled_refresh) {
        this.enabled_refresh = enabled_refresh;
    }

    private boolean intercept = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (intercept) {
            return super.onInterceptTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offset = (startY - ev.getY());
                if (offset < 0 && !canScroll(-1) && enabled_refresh) {
                    return true;
                }

                if (offset > 0 && !canScroll(1) && enabled_load) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float offset = (startY - ev.getY()) / damp;

                if (offset < 0 && !canScroll(-1) && enabled_refresh) {
                    actionMove(offset);
                }

                if (offset > 0 && !canScroll(1) && enabled_load) {
                    actionMove(offset);
                }

                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
            default:
                performClick();
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void actionMove(float offset) {


        LayoutParams params = headerView.getLayoutParams();

        if (params.height - offset > 300) {
            params.height = 300;
            animTitle();
        } else {
            params.height = (int) (params.height - offset);
        }
        headerView.setLayoutParams(params);
    }

    private void animTitle() {
        intercept = true;
        final TextView viewById = headerView.findViewById(R.id.second_title);
        final LayoutParams params = viewById.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(0, 108);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int s = (int) animation.getAnimatedValue();
                params.height = s;
                viewById.setLayoutParams(params);

                LayoutParams params1 = headerView.getLayoutParams();
                params1.height += 108/1500;
                headerView.setLayoutParams(params1);


            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewById.setVisibility(VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                intercept = false;
                viewById.setText("lalalala");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewById.setVisibility(GONE);
                        LayoutParams params1 = headerView.getLayoutParams();
                        params1.height -= 108;
                        headerView.setLayoutParams(params1);
                    }
                }, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void actionUp() {

    }

    /**
     * 是否能够滑动
     *
     * @param type -1 上 1 下
     * @return true 能  false 不能
     **/
    protected boolean canScroll(int type) {
        return contentView.canScrollVertically(type);
    }
}

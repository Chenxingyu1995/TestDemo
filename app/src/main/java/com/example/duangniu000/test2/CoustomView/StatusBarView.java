package com.example.duangniu000.test2.CoustomView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.StatusBarHelper;

public class StatusBarView extends ViewGroup {

    private View status_bar_space;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(getContext(), R.layout.title_bar, this);
        status_bar_space = view.findViewById(R.id.status_space);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        View view = getChildAt(0);
        setMeasuredDimension(widthMeasureSpec, view.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(0, 0, getWidth(), view.getMeasuredHeight());
    }


    @SuppressLint("WrongCall")
    public void setStatusBar(boolean b) {
        if (b) {
            int statusbarHeight = StatusBarHelper.getStatusbarHeight(getContext());
            LayoutParams params = status_bar_space.getLayoutParams();
            params.height = statusbarHeight;
            status_bar_space.setLayoutParams(params);
            status_bar_space.setVisibility(VISIBLE);
            status_bar_space.setAlpha(0.5f);
        } else {
            status_bar_space.setVisibility(GONE);
        }
    }

}

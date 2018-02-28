package com.example.duangniu000.test2.Group;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LadderShapedGroup extends ViewGroup {


    private int mleft = 20;

    public LadderShapedGroup(Context context) {
        super(context);
    }

    public LadderShapedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LadderShapedGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int line_left = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int measuredWidth = view.getMeasuredWidth();
            if (i < 6) {
                view.layout(mleft * i + (i) * (+measuredWidth), 0, (i + 1) * measuredWidth + mleft * i, view.getMeasuredHeight());
            } else {
                int index = i - 6;
                view.layout(measuredWidth / 2 + mleft * (index) + (index) * (+measuredWidth), view.getMeasuredHeight(), measuredWidth / 2 + (index + 1) * (measuredWidth) + index * i, view.getMeasuredHeight() * 2);
            }

        }
    }


}

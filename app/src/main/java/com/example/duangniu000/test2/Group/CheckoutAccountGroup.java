package com.example.duangniu000.test2.Group;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CheckoutAccountGroup extends ViewGroup {
    public CheckoutAccountGroup(Context context) {
        this(context, null);
    }

    public CheckoutAccountGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckoutAccountGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int height = 0;
        int width = 0;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (i % 2 == 0) {
                height += view.getMeasuredHeight();
            } else {
                width += view.getMeasuredWidth();
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        if (count > 4) {
            throw new IllegalArgumentException("child count must be less than <4");
        }

        int left = 0;
        int top = 0;
        int right = 0;
        int buttom = 0;

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            switch (i) {
                case 0:
                    left = 0;
                    top = 0;
                    right = view.getMeasuredWidth();
                    buttom = view.getMeasuredWidth();
                    break;
                case 1:
                    left = view.getMeasuredWidth();
                    top = 0;
                    right = view.getMeasuredWidth() * 2;
                    buttom = view.getMeasuredHeight();
                    break;
                case 2:
                    left = 0;
                    top = view.getMeasuredHeight();
                    right = view.getMeasuredWidth();
                    buttom = view.getMeasuredHeight() * 2;
                    break;
                case 3:
                    left = view.getMeasuredWidth();
                    top = view.getMeasuredHeight();
                    right = view.getMeasuredWidth() * 2;
                    buttom = view.getMeasuredHeight() * 2;
                    break;
            }
            layoutChildView(view, left, top, right, buttom);
        }
    }

    private void layoutChildView(View view, int left, int top, int right, int buttom) {
        view.layout(left, top, right, buttom);
    }
}

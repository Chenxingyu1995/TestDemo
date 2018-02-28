package com.example.duangniu000.test2.CoustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 *
 */

public class RoundFrameLayout extends FrameLayout {

    private int radi = 30;

    public void setRadi(int radi) {
        if (this.radi == radi) return;
        this.radi = radi;
        invalidate();
    }

    public RoundFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        Path path = new Path();
        int w = getWidth();
        int h = getHeight();
        path.addRoundRect(new RectF(0, 0, w, h), radi, radi, Path.Direction.CW);
        canvas.clipPath(path);
        super.draw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Path path = new Path();
        int w = getWidth();
        int h = getHeight();
        path.addRoundRect(new RectF(0, 0, w, h), radi, radi, Path.Direction.CW);
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }

}

package com.example.duangniu000.test2.CoustomView;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class StakeProgressView extends View {

    private int bgColor;
    private int progressColor;
    private float proportion = 0.0f;
    private RectF rect;

    private boolean drawBackground = true;

    public void setProgress(float proportion) {
        this.proportion = proportion;
        drawBackground = false;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public StakeProgressView(Context context) {
        this(context, null);
    }

    public StakeProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StakeProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        bgColor = Color.parseColor("#eeeef0");
        progressColor = Color.parseColor("#4e5dc7");
        rect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawProgressBarBackground(canvas);
        int width = (int) (getMeasuredWidth() * proportion);
        Path path = getPath(width);
        canvas.drawPath(path, getProgressPaint());
    }

    private Paint bgPaint;
    private Path progressbg;
    private Paint progressPaint;


    public Paint getProgressPaint() {
        if (progressPaint == null) {
            progressPaint = new Paint();
            progressPaint.setAntiAlias(true);
            progressPaint.setColor(progressColor);
        }
        return progressPaint;
    }

    public void drawProgressBarBackground(Canvas canvas) {
        getBgPath();
        if (bgPaint == null) {
            bgPaint = new Paint();
            bgPaint.setColor(bgColor);
            bgPaint.setAntiAlias(true);
        }
        canvas.drawPath(progressbg, bgPaint);
    }

    private void getBgPath() {
        if (progressbg == null) {
            progressbg = new Path();
            RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            progressbg.addRoundRect(rectF, getMeasuredHeight() / 2, getMeasuredHeight() / 2, Path.Direction.CCW);
        }
    }


    private Path getPath(int width) {
        Path path = new Path();
        rect.set(0, 0, width, getMeasuredHeight());
        path.addRoundRect(rect, getMeasuredHeight() / 2, getMeasuredHeight() / 2, Path.Direction.CCW);
        return path;
    }
}

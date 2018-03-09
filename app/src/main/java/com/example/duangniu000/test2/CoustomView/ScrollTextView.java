package com.example.duangniu000.test2.CoustomView;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ScrollTextView extends android.support.v7.widget.AppCompatTextView {

    private int scrollLines = 10;


    public ScrollTextView(Context context) {
        super(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private float[] widths;
    private int baseline;
    private float f0 = 0;

    private void getTextWidthOrheight() {
        Paint p = getPaint();
        widths = new float[14];
        p.getTextWidths("0123456789KMB.", widths);
        Paint.FontMetricsInt fontMetrics = p.getFontMetricsInt();
        int measuredHeight = getMeasuredHeight();
        baseline = (measuredHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        f0 = widths[0];
    }
}

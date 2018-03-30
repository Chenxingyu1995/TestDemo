package com.example.duangniu000.test2.CoustomView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


import com.example.duangniu000.test2.R;

public class RoundRectangleTextView extends android.support.v7.widget.AppCompatTextView {

    private int bgColor;
    private int radius;
    private Paint paint;


    public RoundRectangleTextView(Context context) {
        this(context, null);
    }

    public RoundRectangleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectangleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RoundRectangleTextView);
        bgColor = array.getColor(R.styleable.RoundRectangleTextView_background_color, Color.parseColor("#4ed5c7"));
        radius = array.getDimensionPixelOffset(R.styleable.RoundRectangleTextView_round_radius, 12);
        array.recycle();
        paint = new Paint();
        paint.setColor(bgColor);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundResource(0);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        super.onDraw(canvas);
    }
}

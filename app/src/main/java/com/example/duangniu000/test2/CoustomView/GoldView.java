package com.example.duangniu000.test2.CoustomView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.Util;

public class GoldView extends RelativeLayout {

    private ImageView icon;
    private RandomTextView tv;
    private View view;

    public String colorMode = MODE_NORMAL;

    public void setListener(RandomTextView.OnAnimEndListener listener) {
        tv.setListener(listener);
    }

    public static final String MODE_NORMAL = "#999999";
    public static final String MODE_LESS_MILLION = "#e2a979";
    public static final String MODE_MORE_MILLION = "#db6a66";

    public GoldView(Context context) {
        super(context);
        init();
    }

    public GoldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GoldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.ui_gold_layout, this, true);
        icon = view.findViewById(R.id.gold_icon);
        tv = view.findViewById(R.id.text);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        RectF rectF = new RectF(2, 2, getWidth() - 2, getHeight() - 2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(colorMode));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRoundRect(rectF, 20, 20, paint);
    }


    public void setText(String s, boolean isAnim) {
        tv.setText(s);
        if (isAnim) {
            tv.setPianyilian(RandomTextView.FIRSTF_FIRST);
            tv.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setColorMode(String colorMode) {
        this.colorMode = colorMode;
        changeColor();
    }

    public void changeColor() {
        int color = Color.parseColor(colorMode);
        tv.setTextColor(color);
        Drawable background = icon.getDrawable();
        DrawableCompat.setTintList(background, ColorStateList.valueOf(color));
        icon.setImageDrawable(background);
        invalidate();
    }


}

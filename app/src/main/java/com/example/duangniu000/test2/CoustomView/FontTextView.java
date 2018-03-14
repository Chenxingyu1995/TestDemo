package com.example.duangniu000.test2.CoustomView;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class FontTextView extends android.support.v7.widget.AppCompatTextView {

    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        Typeface typeFace = Typeface.createFromAsset(getResources().getAssets(), "fonts/Arvo-Regular.ttf");
        setTypeface(typeFace);
    }

}

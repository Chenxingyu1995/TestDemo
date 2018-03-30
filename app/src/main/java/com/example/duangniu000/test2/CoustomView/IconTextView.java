package com.example.duangniu000.test2.CoustomView;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.Util;


public class IconTextView extends AppCompatTextView {


    private float drawable_width;
    private float drawable_height;

    private int iconType;
    private int iconState = 1;

    /**
     * @param iconType  图标类型
     * @param iconState 图标状态
     **/
    public void setIconType(int iconType, int iconState) {
        this.iconType = iconType;
        this.iconState = iconState;
    }


    public IconTextView(Context context) {
        super(context);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IconTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.IconTextView);
        drawable_width = array.getDimensionPixelSize(R.styleable.IconTextView_icon_width, Util.dip2px(getContext(), 50));
        drawable_height = array.getDimensionPixelSize(R.styleable.IconTextView_icon_height, Util.dip2px(getContext(), 18));
//        event_type = array.getInteger(R.styleable.IconTextView_EventType, 1);
        iconType = array.getInteger(R.styleable.IconTextView_icon_type, 0);
        array.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        switch (iconType) {
            case 0:
                super.setText(text, type);
                break;
            case 1:
                SpannableStringBuilder builder = setEventStatus(text);
                super.setText(builder, type);
                break;
            case 2:
                SpannableStringBuilder builder2 = setStakeStatus(text);
                super.setText(builder2, type);
                break;
        }

    }

    private SpannableStringBuilder setStakeStatus(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text + " ");
        setStakeSpan(builder);
        return builder;
    }

    private void setStakeSpan(SpannableStringBuilder builder) {
        if (builder == null) return;
        Drawable drawable = getStakeDrawableIcon();
        builder.insert(builder.length() - 1, " ");
        VerticalIconSpan span = new VerticalIconSpan(drawable, getContext());
        builder.setSpan(span, builder.length() - 2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }


    private SpannableStringBuilder setEventStatus(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(" " + text);
        setEventSpan(builder, iconState);
        return builder;
    }

    /**
     * 在文字前面添加活动图标
     **/
    private void setEventSpan(SpannableStringBuilder builder, int type) {
        if (builder == null) return;
        Drawable drawable = getEventDrawableIcon(type);
        builder.insert(0, "  ");
        VerticalIconSpan span = new VerticalIconSpan(drawable, getContext());
        builder.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }

    /**
     * 活动状态
     * 2未开始
     * 3结束
     * 1进行中
     * 4已发奖励
     */
    private Drawable getEventDrawableIcon(int type) {
        Drawable drawable = null;
        switch (type) {
            case 1:
                drawable = getResources().getDrawable(R.drawable.shop_commodity_ing);
                drawable.setBounds(0, 0, (int) drawable_width, (int) drawable_height);
                break;
            case 2:
                drawable = getResources().getDrawable(R.drawable.shop_commodity_prepare);
                drawable.setBounds(0, 0, (int) drawable_width, (int) drawable_height);
                break;
            case 3:
                drawable = getResources().getDrawable(R.drawable.shop_commodity_count);
                drawable.setBounds(0, 0, (int) drawable_width, (int) drawable_height);
                break;
            case 4:
                drawable = getResources().getDrawable(R.drawable.shop_commodity_ed);
                drawable.setBounds(0, 0, (int) drawable_width, (int) drawable_height);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.shop_commodity_ing);
                drawable.setBounds(0, 0, (int) drawable_width, (int) drawable_height);
                break;
        }
        return drawable;
    }


    private Drawable getStakeDrawableIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.shop_commodity_ing);
        drawable.setBounds(0, 0, (int) drawable_width, (int) drawable_height);
        return drawable;
    }
}

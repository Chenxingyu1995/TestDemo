package com.example.duangniu000.test2.CoustomView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.BaseMovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 */

@SuppressLint("AppCompatCustomView")
public class ExpendTextView extends TextView implements View.OnTouchListener {


    private String all_string;
    private String sub_string;

    private boolean mIncludePad = true;

    public ExpendTextView(Context context) {
        super(context);
    }

    public ExpendTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public ExpendTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void setIncludeFontPadding(boolean includepad) {
        this.mIncludePad = includepad;
        super.setIncludeFontPadding(includepad);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setAll_string(String all_string) {
        setOnTouchListener(this);
        this.all_string = all_string;
        DynamicLayout dynamicLayout = makeDynamicLayout(all_string);
        setString(dynamicLayout, all_string);
    }


    private void setString(DynamicLayout dynamicLayout, String all_string) {
        int count = dynamicLayout.getLineCount();
        if (count > 8) {
            int lineEnd = dynamicLayout.getLineEnd(7);
            CharSequence charSequence = all_string.substring(0, lineEnd);
            CharSequence sequence = charSequence.subSequence(0, charSequence.length() - 4);
            sub_string = sequence.toString();
            setText(getPartText(sub_string));

        } else {
            setText(all_string);

        }
    }

    private DynamicLayout makeDynamicLayout(String all_string) {
        int paddingLeft = getCompoundPaddingLeft();
        int compoundPaddingRight = getCompoundPaddingRight();
        int left_margin;
        int right_margin;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
        left_margin = params.leftMargin;
        right_margin = params.rightMargin;
        int width = getContext().getResources().getDisplayMetrics().widthPixels - left_margin - right_margin - compoundPaddingRight - paddingLeft;
        float multiplier = getLineSpacingMultiplier();
        float lineSpacingExtra = getLineSpacingExtra();
        return new DynamicLayout(all_string, getPaint(), width, Layout.Alignment.ALIGN_NORMAL, multiplier, lineSpacingExtra, mIncludePad);
    }

    private SpannableStringBuilder getPartText(String substring) {
        substring = substring + "...展开";
        SpannableStringBuilder builder = new SpannableStringBuilder(substring);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                setText(getAllText(all_string));
                requestLayout();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(0xff42d5c7);
                ds.setUnderlineText(false);
            }
        }, builder.length() - 2, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return builder;

    }

    private SpannableStringBuilder getAllText(String substring) {
        substring = substring + "...收起";
        SpannableStringBuilder builder = new SpannableStringBuilder(substring);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                setText(getPartText(sub_string));
                requestLayout();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(0xff42d5c7);
                ds.setUnderlineText(false);
            }
        }, builder.length() - 2, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return builder;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        TextView widget = (TextView) v;
        Object o = widget.getText();

        if (o instanceof Spanned) {
            Spanned buffer = (Spanned) o;
            int action = event.getAction();

            if ( action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);

                if (links.length != 0) {
                    links[0].onClick(widget);
                    return true;
                } else {
                    return false;
                }
            }

        }
        return false;
    }
}

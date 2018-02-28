package com.example.duangniu000.test2.Util;


import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class TextViewUtil {

    public static void setPartText(final TextView textView, final String all_str, final int maxLines) {
        textView.setMovementMethod(null);
        textView.setMaxLines(maxLines);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(all_str);
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Layout layout = textView.getLayout();
                if (layout != null) {
                    int ellipsisCount = layout.getEllipsisCount(layout.getLineCount() - 1);
                    if (ellipsisCount != 0) {
                        String substring = all_str.substring(0, all_str.length() - ellipsisCount - 4);
                        substring = substring.replaceAll("\n", "");
                        substring = substring + "...展开";
                        SpannableStringBuilder builder = new SpannableStringBuilder(substring);
                        builder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                setAllText(textView, all_str, maxLines);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(0xff42d5c7);
                                ds.setUnderlineText(false);
                            }
                        }, builder.length() - 2, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                        textView.setText(builder);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                }
            }
        });

    }

    public static void setAllText(final TextView textView, final String all_str, final int maxLines) {
        textView.setMaxLines(99);
        String text = all_str + "...收起";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                setPartText(textView, all_str, maxLines);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(0xff42d5c7);
                ds.setUnderlineText(false);
            }

        }, builder.length() - 2, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void setPartText2(final TextView textView, final String all_str, final int maxLines)
    {
        textView.setMovementMethod(null);
        textView.setText(all_str);

        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Layout layout = textView.getLayout();
                if (layout != null) {
                    int ellipsisCount = layout.getEllipsisCount(layout.getLineCount() - 1);
                    if (ellipsisCount != 0) {
                        String substring = all_str.substring(0, all_str.length() - ellipsisCount - 4);
                        substring = substring.replaceAll("\n", "");
                        substring = substring + "...展开";
                        SpannableStringBuilder builder = new SpannableStringBuilder(substring);
                        builder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                setAllText(textView, all_str, maxLines);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.setColor(0xff42d5c7);
                                ds.setUnderlineText(false);
                            }
                        }, builder.length() - 2, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                        textView.setText(builder);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                }
            }
        });

    }



}

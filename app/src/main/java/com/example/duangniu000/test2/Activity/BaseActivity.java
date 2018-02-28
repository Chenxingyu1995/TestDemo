package com.example.duangniu000.test2.Activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.duangniu000.test2.Util.DisplayHelper;

import java.lang.reflect.Field;
import java.util.Collection;

public class BaseActivity extends AppCompatActivity {

    protected void hideActionBar() {
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        } catch (NullPointerException e) {

        }
    }

    private int getScreenHeight() {
        return DisplayHelper.getScreenHeight(this);
    }

    private int getScreenWidth() {
        return DisplayHelper.getScreenWidth(this);
    }

    /**
     * 单位转换: dp -> px
     */
    protected int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 单位转换:sp -> px
     */
    protected int px2dp(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


    protected boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    protected boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }


    public int getColorRes(int color_res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(color_res, getTheme());
        } else {
            return getResources().getColor(color_res);
        }


    }
}

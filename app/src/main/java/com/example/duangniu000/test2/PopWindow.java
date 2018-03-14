package com.example.duangniu000.test2;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.duangniu000.test2.Util.ToastUtil;

/**
 *
 */

public class PopWindow extends PopupWindow implements View.OnKeyListener {

    public PopWindow(Context context) {
        super(context);
         View inflate = LayoutInflater.from(context).inflate( 0, null, false);

        inflate.setFocusable(true);
        inflate.setFocusableInTouchMode(true);
        inflate.setOnKeyListener(this);

        setContentView(inflate);
        setFocusable(true);
        setTouchable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);


        setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public void show(View view) {
      showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
//        showAsDropDown(view);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.e("TAG", "onKey: " + "onKey");
        return true;
    }
}

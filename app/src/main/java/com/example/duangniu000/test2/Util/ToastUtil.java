package com.example.duangniu000.test2.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duangniu000.test2.R;


public class ToastUtil {


    private static Toast toast;
    private static TextView textView;

    public ToastUtil() {

    }


    public static void showToast(Context context, String str) {
        if (toast == null || textView == null) {
            synchronized (ToastUtil.class) {
                toast = new Toast(context);
                View view = LayoutInflater.from(context).inflate(R.layout.taost_layout, null);
                textView = view.findViewById(R.id.toast_tv);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
            }
        }
        textView.setText(str);
        toast.show();
    }

    public static void showToast(Context context, String str, int duraiton) {
        if (toast == null) {
            synchronized (ToastUtil.class) {
                toast = Toast.makeText(context, str, duraiton);
            }
        }
        toast.setDuration(duraiton);
        toast.setText(str);
        toast.show();
    }

}

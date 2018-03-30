package com.example.duangniu000.test2.Adaper.ViewHolder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.ParseException;

/**
 *
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    private Activity activity;

    public BaseHolder(View itemView, Activity activity) {
        super(itemView);
        this.activity = activity;
    }

    public BaseHolder(View itemView, Fragment fragment) {
        this(itemView, fragment.getActivity());
    }

    public Activity getActivity() {
        return activity;
    }

    public Context getContext() {
        return activity;
    }

    public abstract void onBindViewHolder(T object);
}


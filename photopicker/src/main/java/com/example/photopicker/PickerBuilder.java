package com.example.photopicker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PickerBuilder {

    public static final int REQUEST_CODE = 0x002;
    public static final int RESULT_CODE = 0x003;

    private Fragment fragment;

    private Activity activity;

    private int maxCount;
    private boolean showGif;
    private boolean showPreView;


    private PickerBuilder(Fragment fragment) {
        this.fragment = fragment;
    }

    private PickerBuilder(Activity activity) {
        this.activity = activity;
    }

    public static PickerBuilder build(Fragment fragment) {
        return new PickerBuilder(fragment);
    }

    public static PickerBuilder build(Activity activity) {
        return new PickerBuilder(activity);
    }

    public PickerBuilder maxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public PickerBuilder showGif(boolean showGif) {
        this.showGif = showGif;
        return this;
    }

    public PickerBuilder showPreView(boolean show) {
        this.showPreView = show;
        return this;
    }

    private Context getContext() {
        return fragment != null ? fragment.getContext() : activity;
    }

    public void start() {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("maxCount", maxCount);
        bundle.putBoolean("showGif", showGif);
        bundle.putBoolean("showPreView", showPreView);
        intent.putExtras(bundle);
        if (activity != null) {
            activity.startActivityForResult(intent, REQUEST_CODE);
        } else {
            fragment.startActivityForResult(intent, REQUEST_CODE);
        }
    }


}

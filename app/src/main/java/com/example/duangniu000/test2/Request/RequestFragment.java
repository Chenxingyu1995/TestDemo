package com.example.duangniu000.test2.Request;


import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.View;

@SuppressLint("ValidFragment")
public class RequestFragment extends Fragment {

    RequestLifecycle lifecycle;

    @SuppressLint("ValidFragment")
    public RequestFragment(RequestLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycle.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }


}

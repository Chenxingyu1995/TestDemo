package com.example.duangniu000.test2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Okhttp.Url;

public class ImageListFragment extends RecyclerViewFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUrl(Url.m1n2 + Url.leixin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected AbstractAdapter onCreateAdapter() {
        return super.onCreateAdapter();
    }

    @Override
    protected void execResponse(Object response) {
        super.execResponse(response);
    }
}

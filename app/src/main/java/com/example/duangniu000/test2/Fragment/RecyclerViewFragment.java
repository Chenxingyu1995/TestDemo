package com.example.duangniu000.test2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Okhttp.GsonCallBack;
import com.example.duangniu000.test2.Okhttp.Params;
import com.example.duangniu000.test2.Okhttp.RequestClient;
import com.example.duangniu000.test2.Okhttp.StringCallBack;
import com.example.duangniu000.test2.RefreshLayout.RefreshLayout2;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;

public class RecyclerViewFragment extends AbsListFragment implements RefreshLayout2.OnRefreshListener {

    protected AbstractAdapter abstractAdapter;
    protected String url;
    protected Class returnClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        abstractAdapter = onCreateAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected AbstractAdapter onCreateAdapter() {
        return null;
    }

    @Override
    protected Params setParams() {
        return Params.build();
    }

    @Override
    protected void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void setResponse(Class t) {
        this.returnClass = t;
    }

    private void netWork() {

        RequestClient.Build().from().url(url).parms(setParams()).newCall(new StringCallBack() {
            @Override
            public void execResponse(Call call, String response) {
                Object json = new Gson().fromJson(response, returnClass);
                RecyclerViewFragment.this.execResponse(json);
            }

            @Override
            public void execFailure(Call call, IOException e) {

            }
        });
    }

    protected void execResponse(Object response) {

    }


    protected void execFailure(Call call, IOException e) {

    }


    @Override
    public void onRefresh(RefreshLayout2 layout) {
        netWork();
    }

    @Override
    public void onLoadMore(RefreshLayout2 layout) {
        netWork();
    }
}

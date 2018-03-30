package com.example.duangniu000.test2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Adaper.ImageListInTypeAdapter;
import com.example.duangniu000.test2.Okhttp.Params;
import com.example.duangniu000.test2.Okhttp.Url;
import com.example.duangniu000.test2.data.ImageList;
import com.example.duangniu000.test2.data.response.ImageListInTypeResponse;

import java.util.List;

/**
 * 类型下图集
 **/
public class ImageListInTypeFragment extends RecyclerViewFragment {

    private int type;
    private int page = 1;
    private int rows = 20;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUrl(Url.m1n2 + Url.tupianji);
        setResponse(ImageListInTypeResponse.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 1);
        }
    }

    @Override
    protected Params setParams() {
        Params setParams = super.setParams();
        setParams.add("type", type);
        setParams.add("page", page);
        setParams.add("rows", rows);
        return setParams;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    protected AbstractAdapter onCreateAdapter() {
        return new ImageListInTypeAdapter(getActivity());
    }

    @Override
    protected void execResponse(Object response) {
        super.execResponse(response);
        if (!isAdded()) return;

        if (response instanceof ImageListInTypeResponse) {
            List<ImageList> lists = ((ImageListInTypeResponse) response).getShowapi_res_body().getData();
            getAbstractAdapter().addAll(lists);
            getAbstractAdapter().notifyDataSetChanged();
        }

        page++;

    }
}

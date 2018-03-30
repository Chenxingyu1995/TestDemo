package com.example.duangniu000.test2.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Adaper.ImageListAdapter;
import com.example.duangniu000.test2.Okhttp.Params;
import com.example.duangniu000.test2.Okhttp.Url;
import com.example.duangniu000.test2.data.response.ImageListResponse;

import java.util.List;

/**
 * id 下图集
 **/
public class ImageListFragment extends RecyclerViewFragment {

    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id", 0);
        }
        setUrl(Url.m1n2 + Url.tupianxiangqing);
        setResponse(ImageListResponse.class);

    }

    @Override
    protected Params setParams() {
        Params params = super.setParams();
        params.put("id", id);
        return params;
    }

    @Override
    protected AbstractAdapter onCreateAdapter() {
        return new ImageListAdapter(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setEnabledRefresh(false);
        swipeRefreshLayout.setEnabledloadMore(false);
    }

    @Override
    protected void execResponse(Object response) {
        super.execResponse(response);

        if (response instanceof ImageListResponse) {
            List<String> data = ((ImageListResponse) response).getShowapi_res_body().getData();
            getAbstractAdapter().addAll(data);
            getAbstractAdapter().notifyDataSetChanged();
        }

    }
}

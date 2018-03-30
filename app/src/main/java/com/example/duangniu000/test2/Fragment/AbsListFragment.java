package com.example.duangniu000.test2.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Okhttp.Params;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.RefreshLayout.RefreshLayout2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbsListFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    protected RefreshLayout2 swipeRefreshLayout;
    Unbinder unbinder;
    protected AbstractAdapter abstractAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    protected abstract AbstractAdapter onCreateAdapter();

    public AbstractAdapter getAbstractAdapter() {

        return abstractAdapter;
    }

    protected abstract Params setParams();

    protected abstract void setUrl(String url);

    protected abstract void setResponse(Class t);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

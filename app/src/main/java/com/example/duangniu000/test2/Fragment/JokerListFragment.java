package com.example.duangniu000.test2.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.duangniu000.test2.Activity.FragmentActivity;
import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Adaper.JokerAdapter;
import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.RefreshLayout.RefreshLayout2;
import com.example.duangniu000.test2.Request.Parms;
import com.example.duangniu000.test2.Request.ReCallBack;
import com.example.duangniu000.test2.Request.RequestClient;
import com.example.duangniu000.test2.data.Joker;
import com.example.duangniu000.test2.data.PagerResponse;
import com.example.duangniu000.test2.data.JokerResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class JokerListFragment extends BaseFragment implements RefreshLayout2.OnRefreshListener, AbstractAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    RefreshLayout2 swipeRefreshLayout;
    Unbinder unbinder;
    private JokerAdapter adapter;
    private int pager = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new JokerAdapter(getActivity());
        adapter.setOnItemClickListener(this);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDecoration());
        swipeRefreshLayout.setEnabledRefresh(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipeRefreshLayout.autoRefresh();
                swipeRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void netWork() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());
        Parms parms = Parms.getInstance();
        parms.add("showapi_appid", "57487");
        parms.add("showapi_sign", "f4cec95e4bb34f249627d873bdd28537");
        parms.add("showapi_timestamp", format);
        parms.add("page", pager);
        parms.add("maxResult", 20);
        RequestClient.Build().url("http://route.showapi.com/341-3").from().parms(parms).newCall(new ReCallBack() {
            @Override
            public void Response(Call call, String response) {

                if (!isAdded()) return;

                pager++;
                JokerResponse jokerResponse = new Gson().fromJson(response, JokerResponse.class);
                PagerResponse body = jokerResponse.getShowapi_res_body();
                List<Joker> contentlist = body.getContentlist();
                int size = adapter.getList().size();
                adapter.addAll(contentlist);
                adapter.notifyItemRangeChanged(size, size + contentlist.size() - 1);
                swipeRefreshLayout.refreshComplete();
            }

            @Override
            public void Failure(Call call, IOException e) {
                swipeRefreshLayout.refreshComplete();
            }


        });
    }

    @Override
    public void onRefresh(RefreshLayout2 layout) {

    }

    @Override
    public void onLoadMore(RefreshLayout2 layout) {
        netWork();
    }

    @Override
    public void onItemClick(int pos, BaseHolder holder) {
        Joker joker = adapter.getList().get(pos);
        Bundle bundle = new Bundle();
        bundle.putString("src", joker.getImg());
        FragmentActivity.launcher(getContext(), ImageFragment.class, bundle);
    }

    public class ItemDecoration extends RecyclerView.ItemDecoration {

        private int topBottom = 10;
        private int leftRight = 5;


        public ItemDecoration() {
            super();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            //判断总的数量是否可以整除
            int totalCount = layoutManager.getItemCount();
            int surplusCount = layoutManager.getSpanCount();
            int childPosition = parent.getChildAdapterPosition(view);

            switch (childPosition % surplusCount) {
                case 0:
                    outRect.set(0, 0, 5, 10);
                    break;
                case 1:
                    outRect.set(5, 0, 0, 10);
                    break;
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {


        }
    }

}

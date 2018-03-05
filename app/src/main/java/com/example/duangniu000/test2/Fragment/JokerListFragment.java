package com.example.duangniu000.test2.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.example.duangniu000.test2.Request.GsonCallBack;
import com.example.duangniu000.test2.Request.Parms;
import com.example.duangniu000.test2.Request.RequestClient;
import com.example.duangniu000.test2.data.Joker;
import com.example.duangniu000.test2.data.JokerResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class JokerListFragment extends BaseFragment implements OnRefreshLoadMoreListener, AbstractAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    SmartRefreshLayout swipeRefreshLayout;
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
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ItemDecoration());
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadMoreListener(this);
        swipeRefreshLayout.setEnableRefresh(false);
        swipeRefreshLayout.setEnableAutoLoadMore(false);
        swipeRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));

        swipeRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        netWork();
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
        parms.add("maxResult", 5);
        RequestClient.Build().url("http://route.showapi.com/341-2").from().parms(parms).newCall(new GsonCallBack<JokerResponse>() {
            @Override
            public void Response(Call call, JokerResponse response) {
                if (!isAdded()) return;
                swipeRefreshLayout.finishLoadMore();
                pager++;
                List<Joker> contentlist = response.getShowapi_res_body().getContentlist();
                int size = adapter.getList().size();
                adapter.addAll(contentlist);
                adapter.notifyItemRangeInserted(size, size + contentlist.size() - 1);


            }

            @Override
            public void Failure(Call call, IOException e) {

            }
        });
    }


    @Override
    public void onItemClick(int pos, BaseHolder holder) {
        Joker joker = adapter.getList().get(pos);
        Bundle bundle = new Bundle();
        bundle.putString("src", joker.getImg());
        FragmentActivity.launcher(getContext(), ImageFragment.class, bundle);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        netWork();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        netWork();
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

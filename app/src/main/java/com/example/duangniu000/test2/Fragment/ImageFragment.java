package com.example.duangniu000.test2.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.duangniu000.test2.Activity.BaseActivity;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Request.DownloadClient;
import com.example.duangniu000.test2.Util.StatusBarHelper;
import com.example.duangniu000.test2.Util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ImageFragment extends BaseFragment implements DownloadClient.LoadListener {


    @BindView(R.id.image)
    ImageView image;
    Unbinder unbinder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String src;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            src = bundle.getString("src");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            StatusBarHelper.translucent(activity);
        }
        RequestOptions options = new RequestOptions();
        options.fitCenter();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!TextUtils.isEmpty(src)) {
            Glide.with(this).load(src).apply(options).into(image);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.arrow_down)
    public void onViewClicked() {
        if (!Util.CheckPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getContext())) {
            Util.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, getActivity(), 200);
        } else {
            DownloadClient build = DownloadClient.build();
            build.setLoadListener(this);
            build.downloadFileGif(src);
        }
    }

    @Override
    public void loadStart() {
        progressBar.setMax(100);
        progressBar.setProgress(0);

    }

    @Override
    public void loading(int pos) {
        progressBar.setProgress(pos);
    }

    @Override
    public void loadEnd() {
        progressBar.setProgress(0);
    }

    @Override
    public void loadFail() {
        progressBar.setProgress(0);
    }
}

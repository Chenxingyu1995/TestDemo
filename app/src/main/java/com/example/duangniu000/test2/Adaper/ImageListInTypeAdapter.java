package com.example.duangniu000.test2.Adaper;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.duangniu000.test2.Activity.FragmentActivity;
import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.Fragment.ImageListFragment;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.data.ImageList;

public class ImageListInTypeAdapter extends AbstractAdapter<ImageList> {

    public ImageListInTypeAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseHolder<ImageList> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListHolder(inflate(R.layout.item_image_or_text, parent, false), getActivity());
    }

    private static class ListHolder extends BaseHolder<ImageList> implements View.OnClickListener {

        private RequestOptions options;
        private ImageView icon;
        private TextView content;
        private ImageList object;

        public ListHolder(View itemView, Activity activity) {
            super(itemView, activity);
            icon = itemView.findViewById(R.id.icon);
            content = itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this);
            options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.centerCrop();
        }

        @Override
        public void onBindViewHolder(ImageList object) {
            this.object = object;
            Glide.with(getActivity()).load(object.getImgurl()).apply(options).into(icon);
            content.setText(object.getTitle());
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", object.getId());
            FragmentActivity.launcher(getActivity(), ImageListFragment.class, bundle);
        }
    }

}

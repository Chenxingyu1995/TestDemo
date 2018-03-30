package com.example.duangniu000.test2.Adaper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.Okhttp.DownloadClient;
import com.example.duangniu000.test2.R;


public class ImageListAdapter extends AbstractAdapter<String> {
    public ImageListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(inflate(R.layout.item_iamge, parent, false), getActivity());
    }

    private static class ImageHolder extends BaseHolder<String> implements View.OnClickListener {
        private ImageView icon;
        private RequestOptions options;
        private String url;

        public ImageHolder(View itemView, Activity activity) {
            super(itemView, activity);
            itemView.setOnClickListener(this);
            icon = itemView.findViewById(R.id.icon);
            options = new RequestOptions();
            options.centerCrop();
        }

        @Override
        public void onBindViewHolder(String object) {
            url = object;
            Glide.with(getContext()).load(object).apply(options).into(icon);
        }

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DownloadClient.build().downLoadFile(url, "jpeg");
                }
            });
            builder.setNeutralButton("取消", null);
            builder.create();
            builder.show();
        }
    }

}

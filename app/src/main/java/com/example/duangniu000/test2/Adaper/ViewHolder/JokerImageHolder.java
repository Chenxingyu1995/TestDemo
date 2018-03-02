package com.example.duangniu000.test2.Adaper.ViewHolder;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.R;

import com.example.duangniu000.test2.data.Joker;

public class JokerImageHolder extends BaseHolder<Joker> implements View.OnClickListener {

    private ImageView imageView;
    private TextView title;
    private RequestOptions options;

    public JokerImageHolder(View itemView, Activity activity) {
        super(itemView, activity);
        options = new RequestOptions();
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        imageView = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.titleTv);
        itemView.setOnClickListener(this);
    }

    private AbstractAdapter.OnItemClickListener listener;

    public void setListener(AbstractAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(Joker o) {

        Glide.with(getContext()).asBitmap().load(o.getImg()).apply(options).into(imageView);
        title.setText(o.getTitle());
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(getAdapterPosition(), this);
        }
    }
}

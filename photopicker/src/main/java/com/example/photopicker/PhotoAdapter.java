package com.example.photopicker;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Image> list;

    private List<String> selectPath;

    private Fragment fragment;

    private int maxCount;

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    private onItemClickListener onItemClickListener;

    void setOnItemClickListener(PhotoAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {

        void onStartCamera();

        void onItemClickListener(Image url, int pos);
    }


    public void select(int pos) {
        if (pos >= list.size()) return;

        if (selectPath.size() >= maxCount) {
            Toast.makeText(fragment.getContext(), "最多选择" + maxCount + "张图片", Toast.LENGTH_SHORT).show();
            return;
        }

        Image image = list.get(pos);
        if (image.isSelect()) {
            image.setSelect(false);
            if (hasPath(image.getPath())) {
                selectPath.remove(image.getPath());
            }

        } else {
            image.setSelect(true);
            if (!hasPath(image.getPath())) {
                selectPath.add(image.getPath());
            }
        }
        notifyItemChanged(pos);
    }

    private boolean hasPath(String path) {
        return selectPath.contains(path);
    }


    PhotoAdapter(Fragment fragment) {
        this.fragment = fragment;
        selectPath = new ArrayList<>();
        list = new ArrayList<>();
        Image c = new Image();
        c.setPath("");
        list.add(c);
    }

    public List<Image> getList() {
        return list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view, fragment, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).onBindViewHolder(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private CheckBox checkBox;
        private Fragment fragment;

        private onItemClickListener listener;

        private RequestOptions options;

        ViewHolder(View itemView, Fragment fragment, onItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.fragment = fragment;
            this.listener = listener;
            imageView = itemView.findViewById(R.id.imageView);
            checkBox = itemView.findViewById(R.id.checkbox);
            options = new RequestOptions();
            options.centerCrop();

        }

        private Image url;

        void onBindViewHolder(Image s) {

            if (getAdapterPosition() == 0) {
//                Glide.with(fragment).load(R.drawable.ic_photo_camera_black_24dp).apply(options).into(imageView);
                imageView.setImageResource(R.drawable.ic_photo_camera_black_24dp);
            } else {
                url = s;
                Glide.with(fragment).load(s.getPath()).apply(options).into(imageView);
                checkBox.setChecked(s.isSelect());
            }
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                if (getAdapterPosition() != 0) {
                    listener.onItemClickListener(url, getAdapterPosition());

                } else {
                    listener.onStartCamera();
                }
            }
        }
    }

    public boolean isMax() {
        return selectPath.size() == maxCount;
    }

    public ArrayList<String> getSelectList() {
        return (ArrayList<String>) selectPath;
    }

    public int getSelectCount() {
        return selectPath.size();
    }

    public boolean addOrDeletePath(Image url) {
        return selectPath.contains(url.getPath());
    }


}

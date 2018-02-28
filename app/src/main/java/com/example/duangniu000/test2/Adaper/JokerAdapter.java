package com.example.duangniu000.test2.Adaper;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.Adaper.ViewHolder.JokerImageHolder;
import com.example.duangniu000.test2.Adaper.ViewHolder.JokerTextHolder;
import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.data.Joker;

public class JokerAdapter extends AbstractAdapter<Joker> {

    private int type;

    public JokerAdapter(Activity activity) {
        super(activity);
        type = 1;
    }

    public JokerAdapter(Activity activity, int type) {
        super(activity);
        this.type = type;
    }

    @Override
    public BaseHolder<Joker> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == 1) {
            View view = inflate(R.layout.item_image_joker, parent, false);
            JokerImageHolder holder = new JokerImageHolder(view, getActivity());
            holder.setListener(onItemClickListener);
            return holder;
        }

        if (type == 2) {
            View view = inflate(R.layout.item_joker_text, parent, false);
            JokerTextHolder holder = new JokerTextHolder(view, getActivity());
            return holder;
        }

        return null;
    }

}

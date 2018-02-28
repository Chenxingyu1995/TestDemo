package com.example.duangniu000.test2.Adaper.ViewHolder;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.data.Joker;

public class JokerTextHolder extends BaseHolder<Joker> {

    private TextView title;
    private TextView content;

    public JokerTextHolder(View itemView, Activity activity) {
        super(itemView, activity);
        title = itemView.findViewById(R.id.titleTv);
        content = itemView.findViewById(R.id.content);
    }

    @Override
    public void onBindViewHolder(Joker o) {
        title.setText(o.getTitle());
        content.setText(o.getText());
    }
}

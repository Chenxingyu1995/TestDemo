package com.example.duangniu000.test2.Adaper;


import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.R;

public class TestAdapter extends AbstractAdapter<String> {

    public TestAdapter(Activity fragment) {
        super(fragment);
    }

    @Override
    public BaseHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextHolder(inflate(R.layout.item_simple_text, parent, false), getActivity());
    }

    private static class TextHolder extends BaseHolder<String> {

        private TextView textView;

        public TextHolder(View itemView, Activity activity) {
            super(itemView, activity);
            textView = itemView.findViewById(R.id.text);
        }

        @Override
        public void onBindViewHolder(String o) {
            textView.setText(o);
        }
    }

}

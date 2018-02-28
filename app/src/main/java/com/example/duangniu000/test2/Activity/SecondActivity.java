package com.example.duangniu000.test2.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.myEvent.Notice;
import com.example.duangniu000.test2.myEvent.UpdateEvent;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends BaseActivity {

    private char[] A_Z = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_secend_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.point)
    public void onViewClicked() {

    }
}

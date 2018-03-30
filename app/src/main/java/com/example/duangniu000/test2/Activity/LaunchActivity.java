package com.example.duangniu000.test2.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.duangniu000.test2.R;
import com.example.duangniu000.test2.Util.ToastUtil;
import com.example.duangniu000.test2.Util.Util;
import com.example.duangniu000.test2.data.DataBaseUtil;
import com.example.photopicker.PhotoPickerFragment;
import com.example.photopicker.PickerBuilder;
import com.jungly.gridpasswordview.GridPasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends BaseActivity {


    @BindView(R.id.pswView)
    GridPasswordView pswView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_top);
        ButterKnife.bind(this);
        final String word = DataBaseUtil.findPassWord();
        Log.e("onCreate", word);
        Util.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, this, 200);

        pswView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (word.equals(psw)) {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
//                    FragmentActivity.launcher(LaunchActivity.this, PhotoPickerFragment.class);

//                    PickerBuilder.build(LaunchActivity.this)
//                            .maxCount(1)
//                            .showGif(false)
//                            .showPreView(false)
//                            .start();

                    finish();
                } else {

                }
            }

            @Override
            public void onInputFinish(String psw) {

            }
        });
    }
}

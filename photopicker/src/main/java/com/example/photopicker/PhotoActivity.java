package com.example.photopicker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    private PhotoPickerFragment pickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        frameLayout = findViewById(R.id.root);
        init(intent);
        boolean permission = Util.CheckPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
        if (permission) {
            Util.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, this, 101);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults == null || grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
    }

    private void init(Intent intent) {
        Bundle extras = intent.getExtras();
        pickerFragment = (PhotoPickerFragment) Fragment.instantiate(this, PhotoPickerFragment.class.getName(), extras);
        getSupportFragmentManager().beginTransaction().add(R.id.root, pickerFragment).show(pickerFragment).commitAllowingStateLoss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "requestCode" + requestCode + "resultCode" + resultCode);
        if (requestCode == UCrop.REQUEST_CROP) {
            pickerFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

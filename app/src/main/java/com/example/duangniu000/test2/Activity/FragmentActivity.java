package com.example.duangniu000.test2.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.duangniu000.test2.R;

public class FragmentActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main_fragment);
        String fragmentName = getIntent().getStringExtra("FragmentName");
        show(fragmentName);
    }


    public static void launcher(Context context, Class fragmentClass) {
        Intent intent = new Intent();
        intent.putExtra("FragmentName", fragmentClass.getName());
        intent.setClass(context, FragmentActivity.class);
        context.startActivity(intent);
    }

    public static void launcher(Context context, Class fragmentClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.putExtra("FragmentName", fragmentClass.getName());
        intent.setClass(context, FragmentActivity.class);
        context.startActivity(intent);
    }

    private void show(String name) {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        try {
            Fragment fragment = Fragment.instantiate(this, name, extras);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragment, name);
            transaction.commitAllowingStateLoss();
        } catch (ClassCastException e) {
            android.app.Fragment fragment = android.app.Fragment.instantiate(this, name, extras);
            android.app.FragmentManager manager = getFragmentManager();
            android.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragment, name);
            transaction.commitAllowingStateLoss();
        }

    }

}

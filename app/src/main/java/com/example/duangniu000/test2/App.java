package com.example.duangniu000.test2;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;


public class App extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static Context getContext() {
        return context;
    }
}

package com.example.duangniu000.test2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


public class App extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static final String TAG = "Lifecy";

    private ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.e(TAG, "onActivityCreated:" + activity.getClass().getName());
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.e(TAG, "onActivityDestroyed:" + activity.getClass().getName());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        registerActivityLifecycleCallbacks(callbacks);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static Context getContext() {
        return context;
    }
}

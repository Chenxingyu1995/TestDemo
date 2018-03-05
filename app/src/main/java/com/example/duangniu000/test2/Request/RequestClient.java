package com.example.duangniu000.test2.Request;


import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.LifecycleListener;
import com.google.gson.Gson;

import java.io.File;
import java.util.Set;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestClient implements RequestLifecycle {

    private int REQUEST_TYPE = GET;
    private String url;
    private Parms parms;

    private static final int GET = 1;
    private static final int JSON = 3;
    private static final int FROM = 4;

    public static RequestClient Build() {
        return new RequestClient();
    }

    public RequestClient url(String url) {
        this.url = url;
        return this;
    }

    public RequestClient parms(Parms parms) {
        this.parms = parms;
        return this;
    }

    private Request.Builder addHeader() {
        return new Request.Builder();
    }

    private final static String TAG = "client";

    public void newCall(Callback callBack) {
        Request.Builder builder = addHeader();
        builder.url(url);
        addRequestBody(builder);
        Request request = builder.build();
        Log.i(TAG, "url:" + request.url());
        Log.i(TAG, "method:" + request.method());
        Log.i(TAG, "Params:" + parms.toString());
        new OkHttpClient().newCall(request).enqueue(callBack);
    }


    private void addRequestBody(Request.Builder request) {
        switch (REQUEST_TYPE) {
            case GET:
                request.get();
                break;
            case FROM:
                MultipartBody formBody = toFrom();
                request.post(formBody);
                break;
            case JSON:
                RequestBody body = toJson();
                request.post(body);
                break;
        }
    }

    /**
     * json
     */
    private RequestBody toJson() {
        String gson = new Gson().toJson(parms);
        return RequestBody.create(MediaType.parse("application/json"), gson);
    }

    /**
     * 表单
     */
    private MultipartBody toFrom() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        Set<String> set = parms.keySet();
        for (String next : set) {
            Object s = parms.get(next);
            if (s instanceof Integer) {
                builder.addPart(MultipartBody.Part.createFormData(next, null, RequestBody.create(MediaTypeCode.getTypeString(), String.valueOf(s))));
            } else if (s instanceof String) {
                builder.addPart(MultipartBody.Part.createFormData(next, null, RequestBody.create(MediaTypeCode.getTypeString(), (String) s)));
            } else if (s instanceof File) {
                builder.addPart(MultipartBody.Part.createFormData(next, ((File) s).getName(), RequestBody.create(MediaTypeCode.getTypeOctetStream(), (File) s)));
            }
        }
        return builder.build();
    }


    private MultipartBody toFrom2() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        Set<String> set = parms.keySet();
        for (String next : set) {
            Object s = parms.get(next);
            if (s instanceof Integer) {
                builder.addFormDataPart(next, String.valueOf(s));
            } else if (s instanceof String) {
                builder.addFormDataPart(next, (String) s);
            } else if (s instanceof File) {
                RequestBody body = RequestBody.create(MediaTypeCode.getTypeOctetStream(), (File) s);
                builder.addFormDataPart(next, ((File) s).getName(), body);
            }
        }
        return builder.build();
    }


    public RequestClient json() {
        this.REQUEST_TYPE = JSON;
        return this;
    }


    public RequestClient from() {
        this.REQUEST_TYPE = FROM;
        return this;
    }


    public void clear() {
        this.parms = null;
        this.url = null;
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
    }
}

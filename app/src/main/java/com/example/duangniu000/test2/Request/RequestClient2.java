package com.example.duangniu000.test2.Request;


import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestClient2 {
    private int requestType = GET;
    private String url;
    private Parms parms;

    private static final int GET = 1;
    private static final int JSON = 3;
    private static final int FROM = 4;


    private RequestClient2() {

    }

    public static RequestClient2 Build() {
        return new RequestClient2();
    }


    public RequestClient2 url(String url) {
        this.url = url;
        return this;
    }

    public RequestClient2 parms(Parms parms) {
        this.parms = parms;
        return this;
    }

    private Request.Builder addHeader() {
        return new Request.Builder();
    }

    private final static String TAG = "client";

    public void newCall(final OnRequestListener listener) {
        Request.Builder builder = addHeader();
        builder.url(url);
        addRequestBody(builder);
        Request request = builder.build();
        Log.i(TAG, "url:" + request.url() + "\nmethod:" + request.method());
        Log.i(TAG, "Params:===>" + parms.toString());
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                listener.onError(call, e);
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull final Response response) throws IOException {

            }
        });
    }


    private void addRequestBody(Request.Builder request) {
        switch (requestType) {
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
                builder.addFormDataPart(next, String.valueOf(s));

            } else if (s instanceof String) {
                builder.addFormDataPart(next, (String) s);
            } else if (s instanceof File) {
                RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), (File) s);
                builder.addFormDataPart(next, ((File) s).getName(), body);
            }
        }
        return builder.build();
    }


    public RequestClient2 json() {
        this.requestType = JSON;
        return this;
    }


    public RequestClient2 from() {
        this.requestType = FROM;
        return this;
    }


    public void clear() {
        this.parms = null;
        this.url = null;
    }

    public interface OnRequestListener {

        void onSuccess(String response);

        void onFailure(Call call, Response response);

        void onError(Call call, IOException e);

    }




    private void onResponse(final Call call, final Response response, final OnRequestListener listener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                if (response.isSuccessful()) {
                    String string = response.body().string();
                    e.onNext(string);
                    e.onComplete();
                } else {
                    e.onNext(Code.ErrorResponse);
                    e.onComplete();
                }

            }
        }).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                if (o.equals(Code.ErrorResponse)) {
                    listener.onSuccess(o);
                } else {
                    listener.onFailure(call, response);
                }
            }
        });
    }
}

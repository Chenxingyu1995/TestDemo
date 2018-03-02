package com.example.duangniu000.test2.Request;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class GsonCallBack<T> implements Callback {

    private Class<T> gsonClass;

    public GsonCallBack() {
        gsonClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private static Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Failure(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            String budy_str = response.body().string();

            final T t = new Gson().fromJson(budy_str, gsonClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Response(call, t);
                }
            });
        }
    }


    public abstract void Response(Call call, T response);

    public abstract void Failure(Call call, IOException e);
}

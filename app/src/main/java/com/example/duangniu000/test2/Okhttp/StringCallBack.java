package com.example.duangniu000.test2.Okhttp;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class StringCallBack implements Callback {
//    private Class<T> tClass;

    /**
     * 获取泛型类型
     */
    public StringCallBack() {
//        tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(@NonNull final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                execFailure(call, e);
            }
        });
    }

    @Override
    public void onResponse(@NonNull final Call call, @NonNull final Response response) throws IOException {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                final String string = body.string();
                Log.i("client", "onResponse: " + string);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        execResponse(call, string);
                    }
                });
            }
        }

    }

    public abstract void execResponse(Call call, String response);

    public abstract void execFailure(Call call, IOException e);


}

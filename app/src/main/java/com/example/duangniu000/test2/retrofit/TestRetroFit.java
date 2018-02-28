package com.example.duangniu000.test2.retrofit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.duangniu000.test2.Request.Parms;
import com.example.duangniu000.test2.Request.Url;
import com.example.duangniu000.test2.Util.ToastUtil;
import com.example.duangniu000.test2.data.JokerResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestRetroFit {

    /**
     * 混合表单形式
     **/
    private void TestMuitPart() {
        //构造 retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.m1n2)
                .build();
        /**
         *构造 RequestBody map
         **/
        HashMap<String, RequestBody> bodyHashMap = new HashMap<>();
        MediaType textType = MediaType.parse("text/plan");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());

        RequestBody body = RequestBody.create(textType, "57487");
        RequestBody body2 = RequestBody.create(textType, "f4cec95e4bb34f249627d873bdd28537");
        RequestBody body3 = RequestBody.create(textType, format);
        RequestBody body4 = RequestBody.create(textType, "1");
        RequestBody body5 = RequestBody.create(textType, "20");

        /**
         * file
         **/
//        File file = new File("");
//        RequestBody filebody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//        bodyHashMap.put("file", filebody);

        bodyHashMap.put("showapi_appid", body);
        bodyHashMap.put("showapi_sign", body2);
        bodyHashMap.put("showapi_timestamp", body3);
        bodyHashMap.put("page", body4);
        bodyHashMap.put("maxResult", body5);

        retrofit.create(Get_interface.class).getCall2(bodyHashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();

                    Log.e("onResponse", string);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * 混合表单形式2
     **/
    public static void TestMuitPart2(final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.m1n2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());
        /**
         *参数类型必须指定，否则无法转换  Object 就不行
         **/
        RequestField parms = new RequestField();
        parms.add("showapi_appid", "57487");
        parms.add("showapi_sign", "f4cec95e4bb34f249627d873bdd28537");
        parms.add("showapi_timestamp", format);
        parms.add("page", 1);
        parms.add("maxResult", 20);

        retrofit.create(Get_interface.class).getCall3(parms.getField()).enqueue(new Callback<JokerResponse>() {
            @Override
            public void onResponse(Call<JokerResponse> call, Response<JokerResponse> response) {
                List list = response.body().getShowapi_res_body().getContentlist();
                Log.e("onResponse", list.toString());
                Log.e("onResponse", "id" + Thread.currentThread().getId());
                ToastUtil.showToast(context,"0000000");
            }

            @Override
            public void onFailure(Call<JokerResponse> call, Throwable t) {

            }
        });
    }

    /**
     * 表单参数形式
     **/
    private void TestFild() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.m1n2)
                .build();

        retrofit.create(Get_interface.class).getCall2("usermane", 1).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}

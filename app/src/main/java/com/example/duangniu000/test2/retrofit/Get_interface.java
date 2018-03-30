package com.example.duangniu000.test2.retrofit;


import com.example.duangniu000.test2.data.response.Response;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface Get_interface {
    @GET("https://www.baidu.com/")
    Call<ResponseBody> getCall();

    @POST("/341-3")
    @FormUrlEncoded
    Call<ResponseBody> getCall2(@FieldMap HashMap<String, RequestBody> map);

    @POST("/341-3")
    @FormUrlEncoded
    Call<ResponseBody> getCall2(@Field("username") String username, @Field("userSex") int sex);

    @POST("/341-3")
    @Multipart
    Call<Response> getCall3(@PartMap HashMap<String, RequestBody> map);

    @POST("{path}")
    @Multipart
    Call<Response> getCall3(@Path("path") String path, @PartMap HashMap<String, RequestBody> map);

    @POST("/151-4")
    @Multipart
    Call<Response> getCall4(@PartMap HashMap<String, RequestBody> map);
}

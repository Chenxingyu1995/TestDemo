package com.example.duangniu000.test2.retrofit;


import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestField {

    private HashMap<String,RequestBody> map = new HashMap<>();

    public void add(String key, Object obj) {
        String str;
        RequestBody body;
        if (obj instanceof CharSequence) {
            str = (String) obj;
            body = RequestBody.create(MediaType.parse("text/plain"), str);
            map.put(key, body);
        } else if (obj instanceof Integer) {
            str = String.valueOf((int) obj);
            body = RequestBody.create(MediaType.parse("text/plain"), str);
            map.put(key, body);
        } else if (obj instanceof File) {
            File file = (File) obj;
            body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            map.put(key, body);
        }
    }

    public HashMap<String, RequestBody> getField() {
        return map;
    }
}

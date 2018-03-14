package com.example.duangniu000.test2.retrofit;


import com.example.duangniu000.test2.Okhttp.MediaTypeCode;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

public class RequestField {

    private HashMap<String, RequestBody> map = new HashMap<>();

    public void add(String key, Object obj) {
        RequestBody body;
        if (obj instanceof CharSequence) {
            String str = (String) obj;
            body = RequestBody.create(MediaTypeCode.getTypeString(), str);
            map.put(key, body);
        } else if (obj instanceof Integer) {
            String str = String.valueOf((int) obj);
            body = RequestBody.create(MediaTypeCode.getTypeString(), str);
            map.put(key, body);
        } else if (obj instanceof File) {
            File file = (File) obj;
            body = RequestBody.create(MediaTypeCode.getTypeOctetStream(), file);
            map.put(key, body);
        }
    }

    public HashMap<String, RequestBody> getField() {
        return map;
    }
}

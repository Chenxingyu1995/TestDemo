package com.example.duangniu000.test2.retrofit;


import com.example.duangniu000.test2.Okhttp.MediaTypeCode;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

public class Field extends HashMap<String, RequestBody> {


    public void put(String key, Object value) {
        RequestBody body;
        if (value instanceof CharSequence) {
            String str = (String) value;
            body = RequestBody.create(MediaTypeCode.getTypeString(), str);
            put(key, body);
        } else if (value instanceof Integer) {
            String str = String.valueOf((int) value);
            body = RequestBody.create(MediaTypeCode.getTypeString(), str);
            put(key, body);
        } else if (value instanceof File) {
            File file = (File) value;
            body = RequestBody.create(MediaTypeCode.getTypeOctetStream(), file);
            put(key, body);
        }
    }
}

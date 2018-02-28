package com.example.duangniu000.test2.Request;


import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Parms extends HashMap<String, Object> {


    private Parms() {
    }

    public static Parms getInstance() {
        Parms parms = new Parms();
        return parms;
    }

    public void add(String key, Object value) {
        super.put(key, value);
    }


}
